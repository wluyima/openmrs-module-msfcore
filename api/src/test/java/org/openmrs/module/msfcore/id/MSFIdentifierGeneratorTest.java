package org.openmrs.module.msfcore.id;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.junit.Test;

public class MSFIdentifierGeneratorTest {

  @Test
  public void getIdentifierForSeed_shouldGenerateAnIdentifierWithinMinLengthAndMaxLengthBounds() throws Exception {
    Calendar date = Calendar.getInstance();
    date.set(Calendar.MONTH, 5);
    date.set(Calendar.YEAR, 2018);
    MSFIdentifierGenerator msfIdentifierGenerator = new MSFIdentifierGenerator("UK", "AB", date.getTime());
    msfIdentifierGenerator.setFirstIdentifierBase("1");
    msfIdentifierGenerator.setCountryCode("UK");
    msfIdentifierGenerator.setLocationCode("AB");
    msfIdentifierGenerator.evaluatePrefix();
    assertThat(msfIdentifierGenerator.getIdentifierForSeed(1), is("UK-AB18.06.1"));
    assertThat(msfIdentifierGenerator.getIdentifierForSeed(26), is("UK-AB18.06.26"));
    assertThat(msfIdentifierGenerator.getIdentifierForSeed(136), is("UK-AB18.06.136"));
    assertThat(msfIdentifierGenerator.getIdentifierForSeed(26980), is("UK-AB18.06.26980"));

    msfIdentifierGenerator.setLocationCode("ABC");
    msfIdentifierGenerator.evaluatePrefix();
    assertThat(msfIdentifierGenerator.getIdentifierForSeed(26980), is("UK-ABC18.06.26980"));

    msfIdentifierGenerator.setCountryCode("UGANDA");
    msfIdentifierGenerator.evaluatePrefix();
    assertThat(msfIdentifierGenerator.getIdentifierForSeed(26980), is("UGANDA-ABC18.06.26980"));
  }
}
