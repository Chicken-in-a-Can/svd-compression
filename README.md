# SVD Compression
Singular Value Decomposion-based image compression  
Only works on square images  
Written in Java for my UTD CS 2336 Final Project  
I would have done it in Rust, but it's a Java class :(  
Doing this cause I needed something to do for it, and I'm in MATH 4334 (Numerical Analysis) & we learned about SVD  
I'm using Apache Commons' Math3 library for doing the singular value decomposition  

Executing the `run` file in the root directory works, for the cactus image
The program accepts 2 arguments, which the `run` file contains  
The first is an input image  
The second is the number of singular values you want to keep  
Lower numbers = more compressed. Higher numbers = better image quality
