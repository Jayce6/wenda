package com.wenda.Service;

import com.wenda.Controller.HomeController;
import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther 张伟豪
 * @create 2019/6/26-16:35
 */
@Service
public class SensitiveService implements InitializingBean {
    private  static  final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is =Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader br=new BufferedReader(inputStreamReader);
            String lineTxt;
            while ((lineTxt=br.readLine())!=null){

                addWord(lineTxt.trim());
            }
            br.close();
            inputStreamReader.close();
            is.close();
        }catch (Exception e){
            logger.error("读取敏感词文件失败"+e.getMessage());
        }
    }

    //构建树
    private void addWord(String lineTxt) {
        TrieNode trieNode = Root;
        for (int i = 0; i <lineTxt.length() ; i++) {
            Character c = lineTxt.charAt(i);
            if (isSymbel(c))  {
                continue;
            }
            TrieNode node = trieNode.getSubNode(c);

            if(node==null){
                node =new TrieNode();
                trieNode.addSubNode(c,node);
            }

            trieNode = node;

            if(i==lineTxt.length()-1){
                trieNode.setKeyWordEnd(true);
            }
        }
    }

    //字典树
    private class TrieNode{
        private boolean end = false;

        private Map<Character,TrieNode> subNodes = new HashMap<>();

        void addSubNode(Character key,TrieNode next) {
            subNodes.put(key,next);
        }

        TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        void setKeyWordEnd(boolean end){
            this.end =end;
        }

        boolean getKeyWordEnd(){
           return end;
        }
    }

    private TrieNode Root = new TrieNode();

    private boolean isSymbel(char c ){
        int ic = (int)c;
        //东亚文字 0x286-- 0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c)&&(ic < 0x286||ic > 0x9FFF);
    }


    //过滤
    public  String  filter(String text){
        if(StringUtils.isEmpty(text)){
            return text;
        }

        String replacement = "**";
        TrieNode tempNode = Root;
        int begin=0;
        int position =0;

        StringBuilder result = new StringBuilder();
        while(position<text.length()) {
            char c = text.charAt(position);
            if(isSymbel(c)){
                if(tempNode == Root){
                    result.append(c);
                    ++begin;
                }
                ++position;
                ++begin;
            }

            tempNode = tempNode.getSubNode(c);


            if(tempNode==null) {
                result.append(text.charAt(begin));
                position=begin+1;
                begin =position;
                tempNode=Root;
            }else if(tempNode.getKeyWordEnd())
            {
                result.append(replacement);
                position+=1;
                begin=position;
                tempNode=Root;
            }else {
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }


}
