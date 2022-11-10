package edu.upc.dsa.minim.Domain.Entity.VO;

import edu.upc.dsa.minim.Domain.Entity.Exceptions.EmailAddressNotValidException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class EmailAddress {
    private String email;

    public EmailAddress(){}

    public EmailAddress(String email) throws EmailAddressNotValidException {
        this.email = email;
        this.isValid();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EmailAddressNotValidException {
        this.email = email;
        this.isValid();
    }

    public boolean isEqual(EmailAddress emailAddress) {
        return (Objects.equals(email, emailAddress.getEmail()));
    }

    public Boolean isValid() throws EmailAddressNotValidException {
        if(!EmailValidator.getInstance().isValid(email)) {
            throw new EmailAddressNotValidException();
        }
        return true;
    }
}
