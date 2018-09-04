package org.openmrs.module.msfcore.id;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;

import org.junit.Test;

public class MSFIdentifierGeneratorTest {

    @Test
    public void getIdentifierForSeed_shouldGenerateAnIdentifierWithinMinLengthAndMaxLengthBounds() throws Exception {
        MSFIdentifierGenerator msfIdentifierGenerator = new MSFIdentifierGenerator("UK", "AB", new SimpleDateFormat("yyyyMM")
                        .parse("201806"));
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
