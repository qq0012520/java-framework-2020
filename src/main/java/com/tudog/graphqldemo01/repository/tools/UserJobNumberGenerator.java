package com.tudog.graphqldemo01.repository.tools;

/**
 * 用户表jobNumber字段生成策略 格式为: u001 -> u002 -> u003
 */
public class UserJobNumberGenerator extends AbstractIdentifierGenerator{

    /**
     * 编号的最大长度，编号不应该超过该长度，否则会出错
     */
    private static int MAX_NUMBER_LENTH = 3;

    /**
     * 初始化编号
     */
    private static int INIT_NUMBER = 1;

    /**
     * 通过下表来映射补零字符，列如：
     * 0 -> '', 1 -> '0', 2 -> '00'
     */
    private static final String[] zeroMap = new String[]{
        "","0","00"
    };

    public UserJobNumberGenerator() {
        //传入对应的表名和列名
        super("user", "job_number");
    }

    @Override
    protected String nextNumber(String currentMaxNumber) {
        if(currentMaxNumber == null){
            return zeroMap[MAX_NUMBER_LENTH - 1] + INIT_NUMBER;
        }
        Integer maxNum = Integer.parseInt(currentMaxNumber);
        String nextNum = Integer.toString(maxNum+ 1);
        System.out.println(nextNum.length());
        int diff = nextNum.length() < MAX_NUMBER_LENTH ? MAX_NUMBER_LENTH - nextNum.length() : 0;
        return zeroMap[diff] + nextNum;
    }
}