#include <jni.h>

JNIEXPORT jstring JNICALL
Java_club_crabglory_www_etcb_micro_MicroFragment_stringFromJNI(JNIEnv *env, jobject instance) {

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}