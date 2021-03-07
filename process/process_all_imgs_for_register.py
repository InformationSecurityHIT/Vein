import sys
import os
import pandas as pd
from PreProcessing_FeatureExtraction import extract_feature

if __name__ == "__main__":

    imagePath = sys.argv[1]  # 获得注册人图片所在文件夹的绝对路径的绝对路径
    destRoot = sys.argv[2]  # 处理后图片所在的文件夹
    # imagePath = "D:\\work\\Java\\code\\DZH\\data_before\\47"
    # destRoot = "D:\\work\\Java\\code\\DZH\\data_after\\47"
    images = os.listdir(imagePath)
    i = 0
    df = pd.DataFrame()
    for image in images:
        print(image)
        name = image.split('\\')[-1].split('.')[0]
        processed_image = extract_feature.pretreatment(imagePath + "\\" + image, name, destRoot)
        features = extract_feature.HOG_feature_extraction_from_numpy(processed_image)
        df[i] = features
        i = i + 1
    feature_data_path = "D:\\InformationSecurityCompetition\\DZH\\features_data\\" + name.split("_")[0] + ".csv"
    df.to_csv(feature_data_path, header=False, index=False)
    print("image process over.")
