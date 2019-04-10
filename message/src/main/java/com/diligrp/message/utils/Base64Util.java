package com.diligrp.message.utils;

import java.util.Base64;

public class Base64Util {

    //编码
    public static String getEncoderString(String str) throws Exception{
        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte = str.getBytes("UTF-8");
       return encoder.encodeToString(textByte);
    }

    //解码
    public static String getDecoderString(String encodedText) throws Exception{
        final Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(encodedText), "UTF-8");
    }
}
