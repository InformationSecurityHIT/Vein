import sys
import numpy as np
import time
import os
import pandas as pd
from PreProcessing_FeatureExtraction import extract_feature


def euclidean(x, y):
    return np.sqrt(np.sum((x - y) ** 2))

def max_list(lt):
    temp = 0
    max_str = 0
    for i in lt:
        if lt.count(i) > temp:
            max_str = i
            temp = lt.count(i)
    return max_str

# 拿着这个人提取出的HOG特征去查找匹配这个人
# 这个地方没有拒绝识别的代码
def search_for_match(feature, dataRoot="D:\InformationSecurityCompetition\DZH\\features_data"):
    registers = os.listdir(dataRoot)
    dis_dic = {}
    for register in registers:
        register_feature = pd.read_csv(dataRoot + "\\" + register, header=None)
        register_feature = register_feature.values
        for i in range(register_feature.shape[1]):
            dis = euclidean(feature, register_feature[:, i])
            dis_dic[dis] = register
    d_order=sorted(dis_dic.items())
    re = []
    j = 0
    for elem in d_order:
        if j<5:
            re.append(elem[1])
        else:
            break
        j +=1
    print(re)
    return max_list(re).split('.')[0]


if __name__ == "__main__":
    imagePath = sys.argv[1]  # 获得待处理图片的绝对路径
    destRoot = sys.argv[2]  # 处理后图片所在的文件夹
    # imagePath = "D:\Lab\Info_Security\DZH\data_before\\71\\71_0.png"
    # destRoot =  "D:\Lab\Info_Security\DZH\\data_after"
    name = imagePath.split('\\')[-1].split('.')[0]  # 这个地方根据文件名命名后续文件可能需要改
    image = extract_feature.pretreatment(imagePath, name, destRoot)
    features = extract_feature.HOG_feature_extraction_from_numpy(image)
    print(search_for_match(features))
