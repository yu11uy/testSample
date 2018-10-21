package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.SignupForm;
import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {
	
	@Autowired
	private UserService userService;
	
	//ラジオボタン
	private Map<String, String>radioMarriage;
	
  /**
   * ラジオボタンの初期化メソッド.
   */
	private Map<String, String>initRadioMarrige() {
			
		Map<String, String>radio = new LinkedHashMap<>();
		//既婚、未婚を格納
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		
		return radio;
		
	}

  /**
   * ユーザー登録画面のGETメソッド用処理.
   */
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form, Model model) {
		
		radioMarriage = initRadioMarrige();
		
		model.addAttribute("radioMarriage", radioMarriage);
		
		return "login/signup";
	}
	
  /**
   * データバインド
   * @ModelAttribute：フォームクラスをModelに登録
   * BindingResult：データバインド結果の受け取り
   * hasErrors()：エラー判定
   * 
   * バリデーション
   * @Validated(GroupOrder.class)：バリデーション実施用アノテーション
   * 引数は入力チェックの順番設定グループクラスを指定
   */	
  /**
   * ユーザー登録画面のPOSTメソッド用処理.
   */
	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form
			, BindingResult bindingResult 
			, Model model) {
		
		if(bindingResult.hasErrors()) {
			return getSignUp(form, model);
		}
		
		System.out.println(form);
		
		//登録用変数
		User user = new User();
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setUserName(form.getUserName());
		user.setBirthday(form.getBirthday());
		user.setAge(form.getAge());
		user.setMarriage(form.isMarriage());
		user.setRole("ROLE_GENERAL");
		
		//ユーザー登録処理
		boolean result = userService.insert(user);
		
		if(result) {
			System.out.println("ユーザー登録成功");
		} else {
			System.out.println("ユーザー登録失敗");
		}
		
		return "redirect:/login";
	}
	
  /**
   * @ExceptionHandler(例外クラス).
   * Exception毎の例外処理を実装
   * コントローラークラス毎に例外処理を実装する場合に使用
   * エラーを起こしやすいControllerクラスに実装
   */
  /**
   * DataAccessException発生時の処理メソッド.
   */
  @ExceptionHandler(DataAccessException.class)
  public String dataAccessExceptionHandler(DataAccessException e, Model model) {

      // 例外クラスのメッセージをModelに登録
      model.addAttribute("error", "内部サーバーエラー（DB）：ExceptionHandler");

      // 例外クラスのメッセージをModelに登録
      model.addAttribute("message", "SignupControllerでDataAccessExceptionが発生しました");

      // HTTPのエラーコード（500）をModelに登録
      model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

      return "error";
  }
  
  /**
   * Exception発生時の処理メソッド.
   */
  @ExceptionHandler(Exception.class)
  public String exceptionHandler(Exception e, Model model) {

      // 例外クラスのメッセージをModelに登録
      model.addAttribute("error", "内部サーバーエラー：ExceptionHandler");

      // 例外クラスのメッセージをModelに登録
      model.addAttribute("message", "SignupControllerでExceptionが発生しました");

      // HTTPのエラーコード（500）をModelに登録
      model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

      return "error";
  }

}
