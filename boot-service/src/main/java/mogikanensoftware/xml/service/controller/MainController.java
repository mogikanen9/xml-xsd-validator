package mogikanensoftware.xml.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogikanensoftware.xml.utils.core.bean.ValidationResult;

@RestController
public class MainController {

	@RequestMapping("/validate")
	public ValidationResult validate(){
		return new ValidationResult();
	}
}
