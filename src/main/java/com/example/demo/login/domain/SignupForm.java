package com.example.demo.login.domain;

import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.login.domain.model.ValidGroup1;
import com.example.demo.login.domain.model.ValidGroup2;
import com.example.demo.login.domain.model.ValidGroup3;

import lombok.Data;

@Data
public class SignupForm {
	
	@NotBlank(groups=ValidGroup1.class)
	@Email(groups=ValidGroup2.class)
//	@NotBlank
//	@Email
	private String userId;
	
	@NotBlank(groups=ValidGroup1.class)
	@Length(min=4, max=100, groups=ValidGroup2.class)
	@Pattern(regexp="^[a-zA-Z0-9]+$", groups=ValidGroup3.class)
//	@NotBlank
//	@Length(min=4, max=100)
//	@Pattern(regexp="^[a-zA-Z0-9]+$")
	private String password;
	
	@NotBlank(groups=ValidGroup1.class)
//	@NotBlank
	private String userName;
	
	@NotNull(groups=ValidGroup1.class)
//	@NotNull
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date birthday;
	
	@Min(value=20, groups=ValidGroup2.class)
	@Max(value=100, groups=ValidGroup2.class)
//	@Min(value=20)
//	@Max(value=100)
	private int age;
	
	@AssertFalse(groups=ValidGroup2.class)
//	@AssertFalse
	private boolean marriage;

}
