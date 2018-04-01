package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class WorldLadderController {
    @RequestMapping("/")
    public String wordLadder(@RequestParam Map<String,Object> paraMap){
        String word1 = paraMap.get("word1").toString();
        String word2 = paraMap.get("word2").toString();
        new WordLadder(word1,word2);
        StringBuffer ans=WordLadder.start();
        if(ans!= null)
            return ans.toString();
        return "Something went wrong!";
    }
}
