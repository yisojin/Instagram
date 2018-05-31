package kr.hs.dgsw.instagram.Common;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leesojin on 2018. 5. 27..
 */

public class IsValid {

    /**
     * @param emailOrTel
     * @return valid is true or false
     * @// TODO: 2018. 5. 27. 이메일과 전화번호 분리
     */
    public boolean isTrue(String emailOrTel) {

        boolean returnValue = false;

        //tel regex
        String regex = "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";

        if (emailOrTel.contains("@")) {
            Log.i("email", emailOrTel);
            //email regex
            regex = " ^[a-zA-Z0-9]+@[a-zA-Z0-9]+$";
        }

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(emailOrTel);

        if (m.matches()) {

            returnValue = true;

        }

        return returnValue;
    }

    public boolean isEmail(String email){
        boolean value;
        Pattern p;
        Matcher m;

        String EMAIL_REGEX = " ^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@" +"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_REGEX);
        m = p.matcher(email);
        value = m.matches();

//        return Patterns.EMAIL_ADDRESS.matcher(emailOrTel).matches();

        return value;
    }

    public boolean isPhone(String tel){

        boolean returnValue = false;
        String regex = "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";

//        return Patterns.PHONE.matcher(emailOrTel).matches();
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(tel);

        if (m.matches()) {

            returnValue = true;
        }
        return returnValue;

    }

}
