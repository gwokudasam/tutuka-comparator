FROM gwokudasam/alpine-jdk14

MAINTAINER Samuel Gwokuda "gwokudasam@gmail.com"

COPY target/transaction-comparator.jar transaction-comparator.jar

EXPOSE 5001

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "transaction-comparator.jar"]

