import time
import numpy as np
import os
import pandas as pd
from PreProcessing_FeatureExtraction import extract_feature


def euclidean(x, y):
    return np.sqrt(np.sum((x - y) ** 2))


# 拿着这个人提取出的HOG特征去查找匹配这个人
# 这个地方没有拒绝识别的代码
def search_for_match(feature, dataRoot="D:\\work\\Java\\code\\DZH\\features_data"):
    registers = os.listdir(dataRoot)
    min_register = ""
    min_i = -1
    min_dis = float('inf')
    sum = 0.0

    for register in registers:
        re = time.time()
        register_feature = pd.read_csv(dataRoot + "\\" + register, header=None)
        register_feature = register_feature.values
        sum = time.time() - re
        for i in range(register_feature.shape[1]):
            dis = euclidean(feature, register_feature[:, i])
            if (dis < min_dis):
                min_i = i
                min_dis = dis
                min_register = register
    return min_register.split('.')[0]


if __name__ == "__main__":
    imagePath = "D:\\work\\Java\\code\\DZH\\data_before\\44\\44_0.png"
    destRoot = "D:\\work\\Java\\code\\DZH\\data_after"
    name = imagePath.split('\\')[-1].split('.')[0]  # 这个地方根据文件名命名后续文件可能需要改
    e1 = time.time()
    image = extract_feature.pretreatment(imagePath, name, destRoot)
    e2 = time.time()
    print("pretrearment:" + str(e2 - e1))
    features = extract_feature.HOG_feature_extraction_from_numpy(image)
    e3 = time.time()
    print("feature:" + str(e3 - e2))
    re = search_for_match(features)
    e4 = time.time()
    print("match" + str(e4 - e3))
