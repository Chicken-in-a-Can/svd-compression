# SVD Compression
Singular Value Decomposion-based image compression  
Only works on square images  
Written in Java for my UTD CS 2336 Final Project  
I would have done it in Rust, but it's a Java class :(  
Doing this cause I needed something to do for it, and I'm in MATH 4334 (Numerical Analysis) & we learned about SVD  
I'm using Apache Commons' Math3 library for doing the singular value decomposition  
Image is chunked into 32x32 pixel squares for efficiency (O(n^3) is an expensive algorithm for 4k images)  

Executing the `run` file in the root directory works, for the cactus image
The program accepts 2 arguments, which the `run` file contains  
The first is whether to compress or decompress  
The second is an input image file name  

# Disclaimer
You may notice file sizes are larger for the compressed image  
This is a worse compression algorithm than like jpeg uses  
And worse implemented  
But it is still compressed by a few times over raw  
