package org.jupi.springtoken;

/**
 * DTO POJO.
 */
public class CreateTokenResponse {

    private final String token;
    private final boolean success;

    public CreateTokenResponse(String token, boolean success) {
        this.token = token;
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "CreateTokenResponse{" +
                "token='" + token + '\'' +
                ", success=" + success +
                '}';
    }
}
