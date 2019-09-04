package edu.jxau.community.service;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: community
 * @ClassName SensitiveFilterService.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Service
public class SensitiveFilterService {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilterService.class);

    /**
     * 替换符
     */
    private String REPLACEMENT = "***";

    /**
     * 根节点
     */
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init(){
        try(InputStream inputStream = Thread.currentThread().
                getContextClassLoader().getResourceAsStream("sensitive-words.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));){

            String keyword;
            while ((keyword = reader.readLine()) != null){
                this.addKeyword(keyword);
            }

        }catch (Exception e){
            logger.error("敏感词加载失败：" + e.getMessage());
        }
    }

    /**
     *
     * @param text
     * @return
     */
    public String filter(String text){
        if (StringUtils.isBlank(text)){
            return null;
        }

        /**
         * 三个指针：
         *     tempNode -> 指向前缀树
         *     begin  -> 开始判定的字符
         *     position -> 移动指向待判定的字符
         */
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        StringBuilder result = new StringBuilder();

        while (position < text.length()){
            char c = text.charAt(position);

            /**
             * 如果当前符号是符号
             *        （1） 如果不是待定敏感词直接添加符号到结果中，begin加一
             *        （2） 反之，不处理
             *        （3） position 总往后移动一位
             */
            if (this.isSymbol(c)){
                if (tempNode == rootNode){
                    result.append(c);
                    begin++;
                }

                position++;
                continue;
            }

            /**
             * 检查当前字符：
             *     （1） 如果是待定敏感词，以begin开头的字符串不是敏感词，进入下一个位置，重新指向根节点
             *     （2） 如果是敏感词的结束，替换begin->position 的字符，进入下一个位置，重新指向根节点
             *     （3） 反之处于待定敏感词中，进入下一个位置判断
             */
            tempNode = tempNode.getChildNode(c);
            if (tempNode == null){
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;

            }else if (tempNode.isKeywordEnd){
                result.append(REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;

            }else{
                position++;
            }
        }

        result.append(text.substring(begin));
        return result.toString();
    }

    /**
     * 判断是否为符号
     * @param c
     * @return
     */
    private boolean isSymbol(char c){
        // 0x2E80~0x9FFF 是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    /**
     * 构造前缀树
     * @param keyword
     */
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;

        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode childNode = tempNode.getChildNode(c);

            if (childNode == null){
                childNode = new TrieNode();
                tempNode.setChildNode(c,childNode);
            }

            tempNode = childNode;

            if (i == (keyword.length()-1)){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 前缀树节点
     */
    private class TrieNode{

        /**
         * 标识是否为一个关键词的结束字符
         */
        private boolean isKeywordEnd = false;

        /**
         * 子节点
         */
        private Map<Character,TrieNode> childNode = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        /**
         * 获取子节点
         * @param character
         * @return
         */
        public TrieNode getChildNode(Character character) {
            return childNode.get(character);
        }

        /**
         * 添加子节点
         * @param character
         * @param node
         */
        public void setChildNode(Character character, TrieNode node) {
            childNode.put(character, node);
        }
    }
}
