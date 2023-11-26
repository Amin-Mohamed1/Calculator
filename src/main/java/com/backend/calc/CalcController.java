
package com.backend.calc;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CalcController {
    @PostMapping("/eval")
	public static String evaluateExpression(@RequestBody String expression){
        try{
            System.out.println(expression);
            return Double.toString(Parser.parseAddSub(new Tokenizer(expression)));
        }catch(Exception e){
            return "Error";
        }
        
	}
}

