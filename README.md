# Tutuka Comparator


### Required software

The following are the initially required software pieces:

1. *JDK 8*  See https://openjdk.java.net/install/
2. *Maven*  Seehttps://maven.apache.org/download.cgi
3. **Docker Desktop**: The fastest way to containerize applications on your desktop, and you can download it from here [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)


#Deployment Manual

To run the application run the following commands 

`mvn clean package -DskipTests` 

`docker-compose up --build -d`

Now go to the web browser and go to `http://localhost:5001/` and select the CSV Files to upload

Once done selecting the files click on the Compare button

## Comparison algorithm
 1. Use the FileUtils.lineIterator method inside the commons.io library. This reads the file one line at a time, meaning one line is loaded into memory at a time
 2. For each line from the first file, iterate through the second file until a similar line is reached then break the iteration.
 
## Space Complexity
O(m+n) where `m` is a line from the first file and `n` is a line from the second file. `m` is `1` and `n` is `1` therefore the space complexity is in fact O(1).

## Time Complexity
O(m*n) where `m` is a line from the first file and `n` is a line from the second file. This can be optimised??? Using a buffer will still iterate over the same number of lines at the same time increasing the space complexity (I DON'T THINK IT'S WISE)

## Trade-Off
The current solution is trading off optimal space complexity for average time complexity. Since we are dealing with files, this trade off can be justified.

## Concerns
Reading one line at a time might be expensive for the operating system's IO functions. A better way of doing this one....??