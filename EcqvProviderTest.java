import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.util.encoders.Hex;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.trustpoint.m2m.AuthorityKeyIdentifier;
import ca.trustpoint.m2m.EntityName;
import ca.trustpoint.m2m.EntityNameAttribute;
import ca.trustpoint.m2m.EntityNameAttributeId;
import ca.trustpoint.m2m.GeneralName;
import ca.trustpoint.m2m.GeneralNameAttributeId;
import ca.trustpoint.m2m.KeyAlgorithmDefinition;
import ca.trustpoint.m2m.KeyUsage;
import ca.trustpoint.m2m.M2mCertificate;
import ca.trustpoint.m2m.M2mSignatureAlgorithmOids;
import ca.trustpoint.m2m.SignatureAlgorithms;
import ca.trustpoint.m2m.util.KeyConversionUtils;

public class EcqvProviderTest {
  @BeforeClass
  public static void initializeTests() {
    Security.addProvider(new BouncyCastleProvider());
  }

  /**
   * In trying to test the M2mCertificates reconstructPrivateKey() method it became apparent it was
   * not trivial to find the CAs ephemeralPrivateKey. These tests work with the EcqvProvider class
   * directly (which M2mCertificate uses for verification)
   *
   * Test method for {@link ca.trustpoint.m2m.ecqv.EcqvProvider#reconstructPublicKey}
   * {@link ca.trustpoint.m2m.ecqv.EcqvProvider#reconstructPrivateKey}
   * {@link ca.trustpoint.m2m.ecqv.EcqvProvider#verifyKeyPair}
   */
  @Test
  public void testReconstructionData() throws Exception {
    // simulate a certificate generated from the CA
    ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime256v1");
    KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");
    g.initialize(ecSpec, new SecureRandom());
    KeyPair pair = g.generateKeyPair();

    // simulate a CA certificate
    KeyPair caKeyPair = g.generateKeyPair();
    
    
    KeyAlgorithmDefinition caKeyDefinition = new KeyAlgorithmDefinition();
    caKeyDefinition.setAlgorithm(M2mSignatureAlgorithmOids.ECQV_SHA256_SECP256R1);

    SignatureAlgorithms caAlgorithm =
        SignatureAlgorithms.getInstance(caKeyDefinition.getAlgorithm().getOid());

    // hash of tbs certificate
    byte[] tbsCertificate = {0x01};
    long startTime = System.nanoTime();
    EcqvProvider provider = new EcqvProvider(caAlgorithm, caKeyDefinition.getParameters());
    KeyReconstructionData keyReconData =
        provider.genReconstructionData(tbsCertificate, pair.getPublic(), caKeyPair.getPrivate());

    // reconstruct publicKey
    PublicKey reconstructedPublicKey = provider.reconstructPublicKey(tbsCertificate,
        keyReconData.getPublicKeyReconstructionData(), caKeyPair.getPublic());

    // reconstruct privateKey
    PrivateKey reconstructedPrivateKey = provider.reconstructPrivateKey(tbsCertificate,
        keyReconData.getPublicKeyReconstructionData(),
        keyReconData.getPrivateKeyReconstructionData(), pair.getPrivate());

    /*
     * using the reconstructed public and private key sign data[] with reconstructedPrivateKey and
     * verify with the reconstructedPublicKey
     */
    byte[] data = "data".getBytes("UTF8");

    Signature sig = Signature.getInstance("ECDSA");
    sig.initSign(reconstructedPrivateKey);
    sig.update(data);
    byte[] signatureBytes = sig.sign();

    sig.initVerify(reconstructedPublicKey);
    sig.update(data);
    long endTime = System.nanoTime();
    long totalTime = endTime - startTime;
    System.out.println("Implicit Certificate Signature bytes " + Hex.toHexString(signatureBytes));
    System.out.println("Runtime of IC is: " + totalTime + "ns");
    
    startTime = System.nanoTime();
    // Construct certificate data
    // A full certificate
    M2mCertificate cert = new M2mCertificate();

    // serialNumber
    byte[] serialNumber = Hex.decode("F964EF36");
    cert.setSerialNumber(serialNumber);

    // cAAlgorithm, CAAlgParams
    caKeyDefinition.setAlgorithm(M2mSignatureAlgorithmOids.ECDSA_SHA512_SECP521R1);
    caKeyDefinition.setParameters(Hex.decode("102030405060708090A0B0C0E0F0"));
    cert.setCaKeyDefinition(caKeyDefinition);

    // issuer
    EntityName issuer = new EntityName();
    issuer.addAttribute(new EntityNameAttribute(EntityNameAttributeId.Country, "CA"));
    issuer.addAttribute(new EntityNameAttribute(EntityNameAttributeId.CommonName, "MyRoot"));
    issuer.addAttribute(new EntityNameAttribute(EntityNameAttributeId.DomainComponent, "DomC"));
    issuer.addAttribute(new EntityNameAttribute(EntityNameAttributeId.OctetsName, "ca2f00"));
    cert.setIssuer(issuer);

    // validFrom
    Calendar calendar = new GregorianCalendar(2016, 7, 1);
    Date validFrom = calendar.getTime();
    cert.setValidFrom(validFrom);

    // validDuration
    cert.setValidDuration(60 * 60 * 24 * 365);

    // subject
    EntityName subject = new EntityName();
    subject.addAttribute(new EntityNameAttribute(EntityNameAttributeId.Country, "CA"));
    subject.addAttribute(new EntityNameAttribute(EntityNameAttributeId.CommonName, "MyTest"));
    subject.addAttribute(new EntityNameAttribute(EntityNameAttributeId.DomainComponent, "DomC"));
    subject.addAttribute(new EntityNameAttribute(EntityNameAttributeId.OctetsName, "ca2f01"));
    cert.setSubject(subject);

    // pKAlgorithm, pKAlgParams
    KeyAlgorithmDefinition publicKeyDefinition = new KeyAlgorithmDefinition();
    publicKeyDefinition.setAlgorithm(M2mSignatureAlgorithmOids.ECDSA_SHA256_SECP256R1);
    publicKeyDefinition.setParameters(Hex.decode("0102030405060708090A0B0C0E0F"));
    cert.setPublicKeyDefinition(publicKeyDefinition);

    // pubKey
    byte[] rawPublicKey =
        Hex.decode(
            "040078EF059D605AB85B6A25A6EF31A1A73A632D3CB04DC606A8CA0B58239661" +
            "68CFAF6131D8D9B53F6BDF6B62946EC4B41D618FA3FF7F8BBFACBFD4F64FE3C3" +
            "3DA9D200A47AE528DC50B6F3876D7F5BA3C082D9927751E1A8C4F934D90942B3" +
            "5C57DFE311B2663E8D0187AD4EDE31BF9CD2AD8317107360522FDB6975AB2CD6" +
            "6DC029981F");
    boolean isCompressed = KeyConversionUtils.isCompressedEcPoint(rawPublicKey);
    cert.setIsPublicKeyCompressed(isCompressed);

    PublicKey publicKey = KeyConversionUtils.convertRawBytestoEcPublicKey(rawPublicKey);
    cert.setPublicKey(publicKey);

    // authKeyId
    AuthorityKeyIdentifier authKeyId = new AuthorityKeyIdentifier();
    authKeyId.setKeyIdentifier(Hex.decode("793F0C56"));
    GeneralName authKeyIdIssuer =
      new GeneralName(GeneralNameAttributeId.DnsName, "authKeyIdIssuer");
    authKeyId.setCertificateIssuer(authKeyIdIssuer);
    authKeyId.setCertificateSerialNumber(new BigInteger(Hex.decode("729CB27DAE30")));
    cert.setAuthorityKeyIdentifier(authKeyId);

    // subjKeyId
    cert.setSubjectKeyIdentifier(Hex.decode("729CB27DAE31"));

    // keyUsage
    KeyUsage keyUsage = new KeyUsage();
    keyUsage.setDigitalSignature(true);
    cert.setKeyUsage(keyUsage);

    // basicConstraints
    cert.setBasicConstraints(5);

    // certificatePolicy
    cert.setCertificatePolicy("1.2.66.148.0.12");

    // subjectAltName
    GeneralName subjectAltName = new GeneralName(GeneralNameAttributeId.DnsName, "subjectAltName");
    cert.setSubjectAlternativeName(subjectAltName);

    // issuerAltName
    GeneralName issuerAltName = new GeneralName(GeneralNameAttributeId.DnsName, "issuerAltName");
    cert.setIssuerAlternativeName(issuerAltName);

    // extendedKeyUsage
    cert.setExtendedKeyUsage("1.3.22.174.22");

    // authInfoAccessOCSP
    URI authInfoAccessOCSP = new URI("https://ocsptest.trustpointinnovation.com");
    cert.setAuthenticationInfoAccessOcsp(authInfoAccessOCSP);

    // cRLDistribPointURI
    URI cRLDistribPointURI = new URI("https://ocsptest.trustpointinnovation.com");
    cert.setCrlDistributionPointUri(cRLDistribPointURI);

    // x509extensions
    String oid1 = "1.5.24.632.0";
    String oid2 = "1.5.24.632.1";
    byte[] value1 = Hex.decode("003a772fb1");
    byte[] value2 = Hex.decode("98f2b10e27");
    cert.addExtension(oid1, true, value1);
    cert.addExtension(oid2, false, value2);

    // cACalcValue
    byte[] caCalcValue = Hex.decode(
      "3081880242014F15CAF8EF38626B2C7CFA85B9544E028668290CADB45F62E215" +
      "3EAAF5A9D51AF5BF0D02F2C057D3856B5CBFB3529C25B8481405924039FA612D" +
      "422AE9A1A85591024201868D3DFE5FC2BEDD2F7468B0B17ED2708E76CD0D37C4" +
      "4F4D0BB88693752046FCFC56D9818B32533B8992923C2C81499400AC44FBBECD" +
      "6324D8AE1DD41EC73A0B2A");
    cert.setCaCalcValue(caCalcValue);
    endTime = System.nanoTime();
    long totalTime2 = endTime - startTime;
    // get encoded data
    byte[] fullCertData = cert.getEncoded();
    System.out.println("Elliptic Curve Digital Signature Algorithm Cert bytes: " + Hex.toHexString(fullCertData));
    System.out.println("Runtime of ECDSA Cert is: " + totalTime2 + "ns");
    double percent =  ((double) (totalTime2 - totalTime))/totalTime2 *100.0;
    System.out.println("IC is approximately " + percent + "% faster");
  }
}
