package com.kapp.kappcore.wtt;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ControlValves {
    @ExcelProperty(index = 1)
    private String ControlValvesNo;
    @ExcelProperty(index = 2)
    private String size;
    @ExcelProperty(index = 3)
    private String purpose;
    @ExcelProperty(index = 4)
    private String pipelineNo;
    @ExcelProperty(index = 5)
    private String pipelineMaterial;
    @ExcelProperty(index = 6)
    private String pipelineSpecifications;


    // 操作条件
    @ExcelProperty(index = 7)
    private String mediaName;
    @ExcelProperty(index = 8)
    private String mediaStatus;
    @ExcelProperty(index = 9)
    private String maxOpsTemperature;
    @ExcelProperty(index = 10)
    private String normalOpsTemperature;
    @ExcelProperty(index = 11)
    private String mineOpsTemperature;
    @ExcelProperty(index = 12)
    private String flowUnit;
    @ExcelProperty(index = 13)
    private String maxFlow;
    @ExcelProperty(index = 14)
    private String normalFlow;
    @ExcelProperty(index = 15)
    private String minNormal;
    @ExcelProperty(index = 16)
    private String pressureUnit;
    @ExcelProperty(index = 17)
    private String maxValveFrontPressure;
    @ExcelProperty(index = 18)
    private String normalValveFrontPressure;
    @ExcelProperty(index = 19)
    private String minValveFrontPressure;
    @ExcelProperty(index = 20)
    private String maxValvePostPressure;
    @ExcelProperty(index = 21)
    private String normalValvePostPressure;
    @ExcelProperty(index = 22)
    private String minValvePostPressure;
    @ExcelProperty(index = 23)
    private String maxPressureDiff;
    @ExcelProperty(index = 24)
    private String normalPressureDiff;
    @ExcelProperty(index = 25)
    private String minPressureDiff;
    @ExcelProperty(index = 26)
    private String closePressure;
    @ExcelProperty(index = 27)
    private String opsDensity;
    @ExcelProperty(index = 28)
    private String standardDensity;
    @ExcelProperty(index = 29)
    private String proportion;
    @ExcelProperty(index = 30)
    private String gasMolecularWeight;
    @ExcelProperty(index = 31)
    private String dynamicViscosity;
    @ExcelProperty(index = 32)
    private String PressureRecoveryCoefficient;
    @ExcelProperty(index = 34)
    private String vaporizationPressure;
    @ExcelProperty(index = 35)
    private String criticalPressure;
    @ExcelProperty(index = 36)
    private String maxCalculateCValue;
    @ExcelProperty(index = 37)
    private String normalCalculateCValue;
    @ExcelProperty(index = 38)
    private String minCalculateCValue;
    @ExcelProperty(index = 39)
    private String noise;
    @ExcelProperty(index = 40)
    private String ValvePositionDuringAirSourceFailure;





    //阀体组件
    /**
     * 型号
     */
    @ExcelProperty(index = 41)
    private String model;
    /**
     * 型式
     */
    @ExcelProperty(index = 43)
    private String type;
    /**
     * 公称通径
     */
    @ExcelProperty(index = 44)
    private String nominalDiameter;
    /**
     * 阀座直径
     */
    @ExcelProperty(index = 45)
    private String valveSeatDiameter;
    /**
     * 额定CV值
     */
    @ExcelProperty(index = 46)
    private String ratedCVValue;
    /**
     * 最大开度
     */
    @ExcelProperty(index = 47)
    private String maxOpeningDegree;
    @ExcelProperty(index = 48)
    private String normalOpeningDegree;
    @ExcelProperty(index = 49)
    private String minOpeningDegree;
    /**
     * 公称压力
     */
    @ExcelProperty(index = 50)
    private String nominalPressure;
    /**
     * 法兰型式
     */
    @ExcelProperty(index = 51)
    private String flangeType;
    /**
     * 阀体材质
     */
    @ExcelProperty(index = 52)
    private String valveBodyMaterial;
    /**
     * 阀芯材质
     */
    @ExcelProperty(index = 53)
    private String valveCoreMaterial;
    /**
     * 阀座材质
     */
    @ExcelProperty(index = 54)
    private String valveSeatMaterial;
    /**
     * 泄漏等级
     */
    @ExcelProperty(index = 55)
    private String leakageLevel;

    /**
     * 流量特性
     */
    @ExcelProperty(index = 56)
    private String flowCharacteristics;

    /**
     * 上盖型式
     */
    @ExcelProperty(index = 57)
    private String upperCoverType;

    /**
     * 填料
     */
    @ExcelProperty(index = 58)
    private String filler;

    /**
     * 作用型式
     */
    @ExcelProperty(index = 58)
    private String typeOfAction;

    //执行机构
    /**
     * 执行机构--型式
     */
    @ExcelProperty(index = 59)
    private String opsInstType;

    /**
     * 执行机构--型号
     */
    @ExcelProperty(index = 60)
    private String opsInstModel;
    /**
     * 执行机构--供气压力
     */
    @ExcelProperty(index = 61)
    private String OpsInstGasSupplyPressure;
    /**
     * 执行机构--弹簧范围
     */
    @ExcelProperty(index = 62)
    private String springRange;

    /**
     * 执行机构--手轮机构
     */
    @ExcelProperty(index = 63)
    private String handwheelMechanism;

    /**
     * 执行机构--关闭压差
     */
    @ExcelProperty(index = 64)
    private String closePressureDiff;

    //定位器
    /**
     * 定位器--型号
     */
    @ExcelProperty(index = 65)
    private String locatorModel;
    /**
     * 定位器--供气压力
     */
    @ExcelProperty(index = 66)
    private String locatorGasSupplyPressure;
    /**
     * 定位器--输入信号
     */
    @ExcelProperty(index = 67)
    private String InputSignal;
    /**
     * 定位器--防爆等级
     */
    @ExcelProperty(index = 68)
    private String explosionProofLevel;
    /**
     * 定位器--气源接口
     */
    @ExcelProperty(index = 69)
    private String airConnection;
    /**
     * 定位器--电气接口
     */
    @ExcelProperty(index = 70)
    private String electricalInterface;

    //附件
    /**
     * 附件--减压阀
     */
    @ExcelProperty(index = 71)
    private String pressureReducingValve;
    /**
     * 附件--电磁阀型号
     */
    @ExcelProperty(index = 72)
    private String electromagneticValveModel;

    /**
     * 附件--电磁阀电压
     */
    @ExcelProperty(index = 73)
    private String electromagneticValveVoltage;

    /**
     * 附件--备注
     */
    @ExcelProperty(index = 74)
    private String note;
}
