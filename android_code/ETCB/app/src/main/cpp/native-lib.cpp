#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_club_crabglory_www_etcb_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_club_crabglory_www_etcb_MainActivity_kevin(JNIEnv *env, jobject instance) {

    // TODO

}