javac bbsSJTU.java
javac RegexPathFilter.java
javac firstMapper.java
javac firstPartitioner.java
javac firstReducer.java
javac Info.java
javac secondMapper.java
javac secondReducer.java
javac JobBuilder.java
javac aJob.java
jar -cvf aJob.jar ./*.class
hdfs dfs -rm -r /athena/Project1/firstOutput
hdfs dfs -rm -r /athena/Project1/secondOutput
hadoop jar aJob.jar aJob /athena/Project1/fSJTUk.txt /athena/Project1/firstOutput /athena/Project1/firstOutput /athena/Project1/secondOutput
