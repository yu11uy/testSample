package com.example.demo.login.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	/*
 実行タイミング--------------------
 Before		メソッドが実行される前
 After			メソッドが実行された後
 Around	メソッド実行の前後
 AfterReturning		メソッドが正常終了した場合だけ
 AfterThrowing		メソッドが異常終了した場合だけ
	
	実行場所-----------------------
	("execution(* *..*.*Controller.*(..))")	対象をパッケージ名やクラス名を正規表現で指定
	("bean(*Controller)")		Bean名でAOPの対象を指定
	("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	指定したアノテーションが付いているメソッド全てを対象。パッケージを含めて指定
	("@within(org.springframework.stereotype.Controller)")
	指定したアノテーションが付いているクラスの全てのメソッドが対象。パッケージを含めて指定
*/
	
/*
	@Before("execution(* *..*.*Controller.*(..))")
	public void startLog(JoinPoint jp) {
		System.out.println("メソッド開始：　"+ jp.getSignature());
	}
	
	@After("execution(* *..*.*Controller.*(..))")
	public void endLog(JoinPoint jp) {
		System.out.println("メソッド終了：　"+ jp.getSignature());
	}
*/
	

	@Around("execution(* *..*.*Controller.*(..))")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {
		
		System.out.println("メソッド開始：　"+ jp.getSignature());
		
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了：　"+ jp.getSignature());
			
			return result;
			
		} catch(Exception e) {
			System.out.println("メソッド異常終了：　"+ jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
	
	//UserDaoクラスのログ出力
	@Around("execution(* *..*.*UserDao.*(..))")
	public Object daoLog(ProceedingJoinPoint jp) throws Throwable {
		
		System.out.println("メソッド開始：　"+ jp.getSignature());
		
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了：　"+ jp.getSignature());
			return result;
			
		} catch(Exception e) {
			System.out.println("メソッド異常終了：　"+ jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
}
