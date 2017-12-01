package com.example.shafy.bakingpal.utils;

import com.example.shafy.bakingpal.R;

/**
 * Created by shafy on 29/11/2017.
 */

public class ImgResUtils {
    public static int getImgRes(String measure){
        int res;
        switch (measure){
            case "CUP":
                res=R.drawable.ic_cup;
                break;
            case "TBLSP":
                res=R.drawable.ic_tbsp;
                break;
            case "TSP":
                res=R.drawable.ic_tsp;
                break;
            case "K":
                res=R.drawable.ic_k;
                break;
            case "G":
                res=R.drawable.ic_g;
                break;
            case "OZ":
                res=R.drawable.ic_oz;
                break;
            default:
                res=R.drawable.ic_unit;
                break;
        }

        return res;
    }
}
