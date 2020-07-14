package com.tudog.graphqldemo01.repository.tools;

/**
 * 用户表jobNumber字段生成策略
 * 格式为: u001 -> u002 -> u003
 */
public class UserJobNumberGenerator extends AbstractIdentifierGenerator{

    /**
     * 通过下表来映射补零字符，列如：
     * 0 -> '', 1 -> '0', 2 -> '00'
     */
    private static final String[] zeroMap = new String[]{
        "","0","00"
    };

    public UserJobNumberGenerator() {
        super("user", "job_number", "u");
    }

    @Override
    public String nextNumber(String currentMaxNumber) {
        int minLength = 3;
        if(currentMaxNumber == null){
            return "001";
        }
        Integer maxNum = Integer.parseInt(currentMaxNumber);
        String nextNum = Integer.toString(maxNum+ 1);
        int diff = nextNum.length() > minLength ? 0 : nextNum.length() - minLength;
        return zeroMap[diff] + nextNum;
    }
    
}