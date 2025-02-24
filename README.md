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

    // Lock the YUV buffer
    AHardwareBuffer_Planes planes;
    if (AHardwareBuffer_lockYCbCr(buffer, AHARDWAREBUFFER_USAGE_CPU_READ_OFTEN | AHARDWAREBUFFER_USAGE_CPU_WRITE_OFTEN, -1, nullptr, &planes) != 0) {
        LOGE("Failed to lock AHardwareBuffer");
        return;
    }

    uint8_t* yPlane = static_cast<uint8_t*>(planes.ycbcr.y);
    uint8_t* uvPlane = static_cast<uint8_t*>(planes.ycbcr.cb);
    uint32_t yStride = planes.ycbcr.ystride;
    uint32_t uvStride = planes.ycbcr.cstride;
    uint32_t chromaStep = planes.ycbcr.chroma_step;

    if (chromaStep != 2) {
        LOGE("Unexpected chroma step: %d. Expected 2 for NV21/NV12.", chromaStep);
        AHardwareBuffer_unlock(buffer, nullptr);
        return;
    }

    int uvHeight = desc.height / 2;
    int uvWidth = desc.width / 2;

    // Convert NV21 to NV12 (Swap U and V)
    for (int row = 0; row < uvHeight; ++row) {
        for (int col = 0; col < uvWidth; ++col) {
            uint8_t* vuPair = uvPlane + row * uvStride + col * 2;
            std::swap(vuPair[0], vuPair[1]);  // Swap V (NV21) to U (NV12)
        }
    }

    // Unlock buffer after modification
    AHardwareBuffer_unlock(buffer, nullptr);
}


