package ruby.loghistory.encrypt;

public class AES256Exception extends RuntimeException {

    public static final String EXCEPTION_MESSAGE = "데이터 암호화 중 오류가 발생했습니다.";

    public AES256Exception() {
        super(EXCEPTION_MESSAGE);
    }
}