//
// Created by sunyoujun on 17/4/27.
//

#include "jni.h"
#include "string"

extern "C"
JNIEXPORT jint JNICALL
Java_com_demo_opengl_MainActivity_getSum(JNIEnv *env, jobject instance) {
    return 0;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_demo_opengl_MainActivity_getSum2(JNIEnv *env, jobject instance, jint i, jint i1) {
    int sum = i + i1;
    std::string result = "sum=" + sum;
    return env->NewStringUTF(result.c_str());
}



