# LLD - Facebook
LLD problems with thier solutions
We will focus on the following set of requirements while designing Facebook:
1. Each member should be able to add information about their basic profile, work
experience, education, etc.
2. Any user of our system should be able to search other members, groups or pages by
their name.
201
202 | P a g e
3. Members should be able to send and accept/reject connection requests from other
members.
4. Members should be able to follow other members without becoming their connection.
5. Members should be able to create groups and pages, as well as join already created
groups and follow pages.
6. Members should be able to create new posts to share with their connections.
7. Members should be able to add comments to posts, as well as like or share a post or
comment.
8. Members should be able to create privacy lists containing their connections. Members
can link any post with a privacy list, to make the post visible to the members of that
privacy list only.
9. Any member should be able to send messages to other members.
10. Any member will be able to add a recommendation for any page.
11. The system should send a notification to a member, whenever there is a new message
or connection invitation or a comment on their post.
12. Members should be able to search through posts for a word.
Extended Requirement: Write a function to find connection suggestion for a member.


#include <jni.h>
#include <android/hardware_buffer.h>
#include <android/log.h>
#include <stdint.h>
#include <cstring>

#define LOG_TAG "NV21toNV12"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C"
JNIEXPORT void JNICALL
Java_com_example_yourapp_NativeUtils_convertNV21ToNV12(JNIEnv* env, jobject thiz, jobject jsrcBuffer) {
    if (!jsrcBuffer) {
        LOGE("Received null HardwareBuffer!");
        return;
    }

    // Get AHardwareBuffer from jobject
    AHardwareBuffer* buffer = AHardwareBuffer_fromHardwareBuffer(env, jsrcBuffer);
    if (!buffer) {
        LOGE("Failed to get AHardwareBuffer");
        return;
    }

    // Describe buffer properties
    AHardwareBuffer_Desc desc;
    AHardwareBuffer_describe(buffer, &desc);

    if (desc.format != AHARDWAREBUFFER_FORMAT_YCbCr_420_888) {
        LOGE("Buffer is not in YCbCr_420_888 format, cannot proceed.");
        return;
    }

    // Lock buffer for CPU access
    void* mappedMemory = nullptr;
    if (AHardwareBuffer_lock(buffer, AHARDWAREBUFFER_USAGE_CPU_READ_OFTEN | AHARDWAREBUFFER_USAGE_CPU_WRITE_OFTEN, -1, nullptr, &mappedMemory) != 0) {
        LOGE("Failed to lock AHardwareBuffer");
        return;
    }

    uint8_t* data = static_cast<uint8_t*>(mappedMemory);
    int frameSize = desc.width * desc.height;

    uint8_t* yPlane = data;
    uint8_t* uvPlane = data + frameSize;  // NV21: Y followed by VU plane

    // Convert NV21 (YVU) to NV12 (YUV) in-place
    for (int i = 0; i < frameSize / 2; i += 2) {
        std::swap(uvPlane[i], uvPlane[i + 1]);  // Swap V and U
    }

    // Unlock buffer
    AHardwareBuffer_unlock(buffer, nullptr);
}

