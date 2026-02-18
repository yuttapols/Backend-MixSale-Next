FROM openjdk:17-jdk

COPY target/*.jar best-store.jar 
#COPY . .
EXPOSE 8080

## add folder iamges
# COPY ./images/input/problem/ /images/input/problem/
# COPY ./images/input/product/ /images/input/product/
# COPY ./images/input/profile/ /images/input/profile/

# COPY ./images/output/problem/ /images/output/problem/
# COPY ./images/output/product/ /images/output/product/
# COPY ./images/output/profile/ /images/output/profile/

## file Master

COPY ./images/report/LogoMixSale.png /images/report/LogoMixSale.png
COPY ./REPORT/PDF/StockReport.jrxml /REPORT/PDF/StockReport.jrxml
COPY ./REPORT/EXCEL/StockExcelTemplate.xlsx /REPORT/EXCEL/StockExcelTemplate.xlsx


ENTRYPOINT ["java","-jar","/best-store.jar"]