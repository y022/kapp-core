package com.kapp.kappcore.wtt;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kapp.kappcore.annotaion.ExcelPosition;
import lombok.Data;

import java.io.Serializable;

@Data
public class ControlValves extends  ExcelModel{
    /**
     * 位号
     */
    @ExcelProperty(index = 1)
    @ExcelPosition(cellRef = "D8")
    private String controlValvesNo;
    /**
     * 数量
     */
    @ExcelProperty(index = 2)
    @ExcelPosition(cellRef = "D9")
    private String size;
    /**
     * 用途
     */
    @ExcelProperty(index = 3)
    @ExcelPosition(cellRef = "D10")
    private String purpose;
    /**
     * 管道编号
     */
    @ExcelProperty(index = 4)
    @ExcelPosition(cellRef = "D12")
    private String pipelineNo;
    /**
     * 管道编号
     */
    @ExcelProperty(index = 5)
    @ExcelPosition(cellRef = "D13")
    private String pipelineMaterial;
    /**
     * 管道规格
     */
    @ExcelProperty(index = 6)
    @ExcelPosition(cellRef = "D14")
    private String pipelineSpecifications;


    // 操作条件
    /**
     * 流体名称
     */
    @ExcelProperty(index = 7)
    @ExcelPosition(cellRef = "D16")
    private String mediaName;
    /**
     * 流体状态
     */
    @ExcelProperty(index = 8)
    @ExcelPosition(cellRef = "D17")
    private String mediaStatus;

    /**
     * 最大操作温度
     */
    @ExcelProperty(index = 9)
    @ExcelPosition(cellRef = "D18")
    private String maxOpsTemperature;
    /**
     * 正常温度
     */
    @ExcelProperty(index = 10)
    @ExcelPosition(cellRef = "D19")
    private String normalOpsTemperature;

    /**
     * 最小操作温度
     */
    @ExcelProperty(index = 11)
    @ExcelPosition(cellRef = "D20")
    private String mineOpsTemperature;

    /**
     * 流量单位
      */
    @ExcelProperty(index = 12)
    @ExcelPosition(cellRef = "D21")
    private String flowUnit;

    /**
     * 最大流量
     */
    @ExcelProperty(index = 13)
    @ExcelPosition(cellRef = "D22")
    private String maxFlow;
    /**
     * 正常流量
     */
    @ExcelProperty(index = 14)
    @ExcelPosition(cellRef = "D23")
    private String normalFlow;
    /**
     * 最小流量
     */
    @ExcelProperty(index = 15)
    @ExcelPosition(cellRef = "D24")
    private String minNormal;
    /**
     * 压力单位
     */
    @ExcelProperty(index = 16)
    @ExcelPosition(cellRef = "D25")
    private String pressureUnit;
    /**
     * 最大阀前压力
     */
    @ExcelProperty(index = 17)
    @ExcelPosition(cellRef = "D26")
    private String maxValveFrontPressure;
    /**
     * 正常阀前压力
     */
    @ExcelProperty(index = 18)
    @ExcelPosition(cellRef = "D27")
    private String normalValveFrontPressure;
    /**
     * 最小阀前压力
     */
    @ExcelProperty(index = 19)
    @ExcelPosition(cellRef = "D28")
    private String minValveFrontPressure;
    /**
     * 最大阀后压力
     */
    @ExcelProperty(index = 20)
    @ExcelPosition(cellRef = "D29")
    private String maxValvePostPressure;
    /**
     * 正常阀后压力
     */
    @ExcelProperty(index = 21)
    @ExcelPosition(cellRef = "D30")
    private String normalValvePostPressure;
    /**
     * 最小阀后压力
     */
    @ExcelProperty(index = 22)
    @ExcelPosition(cellRef = "D31")
    private String minValvePostPressure;
    /**
     * 最大压差
     */
    @ExcelProperty(index = 23)
    @ExcelPosition(cellRef = "D32")
    private String maxPressureDiff;
    /**
     * 正常压差
     */
    @ExcelProperty(index = 24)
    @ExcelPosition(cellRef = "D33")
    private String normalPressureDiff;
    /**
     * 最小压差
     */
    @ExcelProperty(index = 25)
    @ExcelPosition(cellRef = "D34")
    private String minPressureDiff;
    /**
     * 关闭压力
     */
    @ExcelProperty(index = 26)
    @ExcelPosition(cellRef = "D35")
    private String closePressure;
    /**
     * 操作密度
     */
    @ExcelProperty(index = 27)
    @ExcelPosition(cellRef = "D36")
    private String opsDensity;
    /**
     * 标准密度
     */
    @ExcelProperty(index = 28)
    @ExcelPosition(cellRef = "D37")
    private String standardDensity;
    /**
     * 比重
     */
    @ExcelProperty(index = 29)
    @ExcelPosition(cellRef = "D38")
    private String proportion;
    /**
     * 气体分子量
     */
    @ExcelProperty(index = 30)
    @ExcelPosition(cellRef = "D39")
    private String gasMolecularWeight;
    /**
     * 动力粘度
     */
    @ExcelProperty(index = 31)
    @ExcelPosition(cellRef = "D40")
    private String dynamicViscosity;
    /**
     * 压力恢复系数
     */
    @ExcelProperty(index = 32)
    @ExcelPosition(cellRef = "D41")
    private String pressureRecoveryCoefficient;
    /**
     * 汽化压力
     */
    @ExcelProperty(index = 33)
    @ExcelPosition(cellRef = "D42")
    private String vaporizationPressure;
    /**
     * 临界压力
     */
    @ExcelProperty(index = 34)
    @ExcelPosition(cellRef = "D43")
    private String criticalPressure;
    /**
     * 最大计算CV值
     */
    @ExcelProperty(index = 35)
    @ExcelPosition(cellRef = "D44")
    private String maxCalculateCValue;
    /**
     * 正常计算CV值
     */
    @ExcelProperty(index = 36)
    @ExcelPosition(cellRef = "D45")
    private String normalCalculateCValue;
    /**
     * 最小计算CV值
     */
    @ExcelProperty(index = 37)
    @ExcelPosition(cellRef = "D46")
    private String minCalculateCValue;
    /**
     * 噪音
     */
    @ExcelProperty(index = 38)
    @ExcelPosition(cellRef = "D47")
    private String noise;
    /**
     * 气源故障时阀位
     */
    @ExcelProperty(index = 39)
    @ExcelPosition(cellRef = "D48")
    private String valvePositionDuringAirSourceFailure;


    //阀体组件
    /**
     * 型号
     */
    @ExcelProperty(index = 40)
    @ExcelPosition(cellRef = "I9")
    private String model;
    /**
     * 型式
     */
    @ExcelProperty(index = 41)
    @ExcelPosition(cellRef = "I10")
    private String type;
    /**
     * 公称通径
     */
    @ExcelProperty(index = 42)
    @ExcelPosition(cellRef = "I11")
    private String nominalDiameter;
    /**
     * 阀座直径
     */
    @ExcelProperty(index = 43)
    @ExcelPosition(cellRef = "I12")
    private String valveSeatDiameter;
    /**
     * 额定CV值
     */
    @ExcelProperty(index = 44)
    @ExcelPosition(cellRef = "I13")
    private String ratedCVValue;
    /**
     * 最大开度
     */
    @ExcelProperty(index = 45)
    @ExcelPosition(cellRef = "I14")
    private String maxOpeningDegree;
    /**
     * 正常开度
     */
    @ExcelProperty(index = 46)
    @ExcelPosition(cellRef = "I15")
    private String normalOpeningDegree;
    @ExcelProperty(index = 47)
    @ExcelPosition(cellRef = "I16")
    private String minOpeningDegree;
    /**
     * 公称压力
     */
    @ExcelProperty(index = 48)
    @ExcelPosition(cellRef = "I17")
    private String nominalPressure;
    /**
     * 法兰型式
     */
    @ExcelProperty(index = 49)
    @ExcelPosition(cellRef = "I18")
    private String flangeType;
    /**
     * 阀体材质
     */
    @ExcelProperty(index = 50)
    @ExcelPosition(cellRef = "I19")
    private String valveBodyMaterial;
    /**
     * 阀芯材质
     */
    @ExcelProperty(index = 51)
    @ExcelPosition(cellRef = "I20")
    private String valveCoreMaterial;
    /**
     * 阀座材质
     */
    @ExcelProperty(index = 52)
    @ExcelPosition(cellRef = "I21")
    private String valveSeatMaterial;
    /**
     * 泄漏等级
     */
    @ExcelProperty(index = 53)
    @ExcelPosition(cellRef = "I22")
    private String leakageLevel;

    /**
     * 流量特性
     */
    @ExcelProperty(index = 54)
    @ExcelPosition(cellRef = "I23")
    private String flowCharacteristics;

    /**
     * 上盖型式
     */
    @ExcelProperty(index = 55)
    @ExcelPosition(cellRef = "I24")
    private String upperCoverType;

    /**
     * 填料
     */
    @ExcelProperty(index = 56)
    @ExcelPosition(cellRef = "I25")
    private String filler;

    /**
     * 作用型式
     */
    @ExcelProperty(index = 57)
    @ExcelPosition(cellRef = "I26")
    private String typeOfAction;

    //执行机构
    /**
     * 执行机构--型式
     */
    @ExcelProperty(index = 58)
    @ExcelPosition(cellRef = "I28")
    private String opsInstType;

    /**
     * 执行机构--型号
     */
    @ExcelProperty(index = 59)
    @ExcelPosition(cellRef = "I29")
    private String opsInstModel;
    /**
     * 执行机构--供气压力
     */
    @ExcelProperty(index = 60)
    @ExcelPosition(cellRef = "I30")
    private String OpsInstGasSupplyPressure;
    /**
     * 执行机构--弹簧范围
     */
    @ExcelProperty(index = 61)
    @ExcelPosition(cellRef = "I31")
    private String springRange;

    /**
     * 执行机构--手轮机构
     */
    @ExcelProperty(index = 62)
    @ExcelPosition(cellRef = "I32")
    private String handwheelMechanism;

    /**
     * 执行机构--关闭压差
     */
    @ExcelProperty(index = 63)
    @ExcelPosition(cellRef = "I33")
    private String closePressureDiff;

    //定位器
    /**
     * 定位器--型号
     */
    @ExcelProperty(index = 64)
    @ExcelPosition(cellRef = "I35")
    private String locatorModel;
    /**
     * 定位器--供气压力
     */
    @ExcelProperty(index = 65)
    @ExcelPosition(cellRef = "I36")
    private String locatorGasSupplyPressure;
    /**
     * 定位器--输入信号
     */
    @ExcelProperty(index = 66)
    @ExcelPosition(cellRef = "I37")
    private String InputSignal;
    /**
     * 定位器--防爆等级
     */
    @ExcelProperty(index = 67)
    @ExcelPosition(cellRef = "I38")
    private String explosionProofLevel;
    /**
     * 定位器--气源接口
     */
    @ExcelProperty(index = 68)
    @ExcelPosition(cellRef = "I39")
    private String airConnection;
    /**
     * 定位器--电气接口
     */
    @ExcelProperty(index = 69)
    @ExcelPosition(cellRef = "I40")
    private String electricalInterface;

    //附件
    /**
     * 附件--减压阀
     */
    @ExcelProperty(index = 70)
    @ExcelPosition(cellRef = "I42")
    private String pressureReducingValve;
    /**
     * 附件--电磁阀型号
     */
    @ExcelProperty(index = 71)
    @ExcelPosition(cellRef = "I43")
    private String electromagneticValveModel;

    /**
     * 附件--电磁阀电压
     */
    @ExcelProperty(index = 72)
    @ExcelPosition(cellRef = "I44")
    private String electromagneticValveVoltage;

    /**
     * 附件--备注
     */
    @ExcelProperty(index = 73)
    @ExcelPosition(cellRef = "I45")
    private String note;
}
