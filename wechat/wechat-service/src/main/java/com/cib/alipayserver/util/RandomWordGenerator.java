package com.cib.alipayserver.util;

import java.util.Random;
/***
 * 
 *  文件名：RandomWordGenerator.java<br><br>
 * 
 *  类名：随机数生成器<br><br>
 *
 *	ver	       变更日期           修改人       修改说明<br>
 *  V1.00  2011-6-10   yelm    初版<br>
 *  ──────────────────────────────────<br>
 *  Copyright (c) 2006,2010 CIB All Rights Reserved. <br>
 *  LICENSE INFORMATION
 */
public class RandomWordGenerator implements WordGenerator {
    private final char[] possiblesChars;

    private final Random myRandom = new Random();

    public RandomWordGenerator(String acceptedChars) {
        possiblesChars = acceptedChars.toCharArray();
    }

	public RandomWordGenerator(){
		possiblesChars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9' };
    }

    @Override
	public String getWord(Integer lenght) {
        StringBuffer word = new StringBuffer(lenght.intValue());
        for (int i = 0; i < lenght.intValue(); i++) {
            word.append(possiblesChars[myRandom.nextInt(possiblesChars.length)]);
        }
        return word.toString();
    }

   
}
