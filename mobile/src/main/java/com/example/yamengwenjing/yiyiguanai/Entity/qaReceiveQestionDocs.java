package com.example.yamengwenjing.yiyiguanai.Entity;

import java.util.List;

/**
 * Created by yamengwenjing on 2016/5/7.
 */
public class qaReceiveQestionDocs {

    public int highScoreDoc;
    public List<docsInfo> docs;

    public static class docsInfo{

        public String text;
        List<Integer> qtypes;
    }
}
