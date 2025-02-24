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
#include <media/NdkImage.h>
#include <media/NdkImageReader.h>
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

    // Convert HardwareBuffer to AImage
    AImage* image = nullptr;
    if (AImage_fromHardwareBuffer(env, jsrcBuffer, &image) != AMEDIA_OK || !image) {
        LOGE("Failed to get AImage from HardwareBuffer");
        return;
    }

    // Get YUV planes
    uint8_t* yData = nullptr;
    uint8_t* uvData = nullptr;
    int yStride = 0, uvStride = 0;
    int yLen = 0, uvLen = 0;

    // Get Y plane
    if (AImage_getPlaneData(image, 0, &yData, &yLen) != AMEDIA_OK ||
        AImage_getPlaneRowStride(image, 0, &yStride) != AMEDIA_OK) {
        LOGE("Failed to get Y plane");
        AImage_delete(image);
        return;
    }

    // Get UV plane (NV21: interleaved VU format)
    if (AImage_getPlaneData(image, 1, &uvData, &uvLen) != AMEDIA_OK ||
        AImage_getPlaneRowStride(image, 1, &uvStride) != AMEDIA_OK) {
        LOGE("Failed to get UV plane");
        AImage_delete(image);
        return;
    }

    int width, height;
    AImage_getWidth(image, &width);
    AImage_getHeight(image, &height);

    int uvHeight = height / 2;
    int uvWidth = width / 2;

    // Convert NV21 (YVU) to NV12 (YUV) by swapping U and V
    for (int row = 0; row < uvHeight; ++row) {
        for (int col = 0; col < uvWidth; ++col) {
            uint8_t* vuPair = uvData + row * uvStride + col * 2;
            std::swap(vuPair[0], vuPair[1]);  // Swap V (NV21) to U (NV12)
        }
    }

    // Cleanup
    AImage_delete(image);
}


