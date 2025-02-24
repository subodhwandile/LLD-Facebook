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



void convertNV21toNV12(const uint8_t* nv21, uint8_t* nv12, int width, int height) {
    int ySize = width * height;
    int uvSize = ySize / 2;

    // Copy the Y plane (same in both formats)
    memcpy(nv12, nv21, ySize);

    // Swap U and V components in the interleaved plane
    for (int i = 0; i < uvSize; i += 2) {
        nv12[ySize + i] = nv21[ySize + i + 1]; // U in NV12 = V in NV21
        nv12[ySize + i + 1] = nv21[ySize + i]; // V in NV12 = U in NV21
    }
}
