package com.silviolimeira.desafio.tools;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.regex.Pattern;

public class ToolBase64 {

    public static void main(String[] args) {
        encodeIp("192.168.0.23");
    }

    public static void encodeIp(String ip) {
        System.out.println("Ip: " + ip);
        System.out.println(ip.split(Pattern.quote(".")));
        String[] parts = ip.split(Pattern.quote("."));
        Integer ipValue = (Short.parseShort(parts[0]) << 24) |
                (Short.parseShort(parts[1]) << 16) |
                (Short.parseShort(parts[2]) << 8) |
                Short.parseShort(parts[3]);


        System.out.println("ip value: " + ipValue);
        byte[] encodedBytes = Base64.getEncoder().encode(ByteBuffer.allocate(4).putInt(ipValue).array());
        String base64String = new String(encodedBytes);
        System.out.println("ip base 64: " + base64String);
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        ByteBuffer wrapped = ByteBuffer.wrap(decodedBytes); // big-endian by default
        int num = wrapped.getInt();
        System.out.println("decode ip base 64: " + num);
    }

}
