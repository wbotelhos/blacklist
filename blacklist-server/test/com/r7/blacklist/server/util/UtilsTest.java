package com.r7.blacklist.server.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void shouldDecodeText() {
		// given
		String text = "aÃ§Ã£o";

		// when
		String actual = Utils.decoderText(text);

		// then
		assertEquals("deve fazer o decode", "ação", actual);
	}

}
