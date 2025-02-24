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


void convertNV21toNV12(uint8_t* dst_y, int dst_stride_y,
                       uint8_t* dst_vu, int dst_stride_vu,
                       int width, int height) {
    // Y plane is already in the correct format, so no changes are needed.

    // Process the VU plane (swap U and V components)
    for (int y = 0; y < height / 2; y++) { // VU plane has half the height of the Y plane
        for (int x = 0; x < width; x += 2) { // Process 2 pixels at a time (V and U)
            // Calculate the index for the current pair of V and U components
            int vu_index = y * dst_stride_vu + x;

            // Swap V and U components
            uint8_t temp = dst_vu[vu_index]; // V component
            dst_vu[vu_index] = dst_vu[vu_index + 1]; // V = U
            dst_vu[vu_index + 1] = temp; // U = V
        }
    }
}
