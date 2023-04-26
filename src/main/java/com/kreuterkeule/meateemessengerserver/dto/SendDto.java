package com.kreuterkeule.meateemessengerserver.dto;

public class SendDto {

    private String text;
    private String AESKeyEnc;
    private String toToken;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAESKeyEnc() {
        return AESKeyEnc;
    }

    public void setAESKeyEnc(String AESKeyEnc) {
        this.AESKeyEnc = AESKeyEnc;
    }

    public String getToToken() {
        return toToken;
    }

    public void setToToken(String toToken) {
        this.toToken = toToken;
    }

    public SendDto() {
    }

    public SendDto(String text, String AESKeyEnc, String toToken) {
        this.text = text;
        this.AESKeyEnc = AESKeyEnc;
        this.toToken = toToken;
    }
}
