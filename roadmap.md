# Versions descriptions
## 1.0.0
### API endpoints
#### materials
1. GET /api/v1/materials (pagination & filters) - get list
2. GET /api/v1/materials/{number} - get detail info about certain

### Kafka consume events
#### materials
1. Increase count of material: create/update (Material receipt)
2. Material consumption: reduce count of material (Material consumption)







### below scratches

# APIs

Materials (сырье)
PurchasedItems(покупное не требующие доработки)
SemiPart(полуфабрикаты)


## Materials 
### REST API
* GET /api/v1/materials (pagination & filters) - get list
* GET /api/v1/materials/{number} - get detail info about certain 

### Kafka consume
* Increase count of material: create/update (Material receipt)
* Material consumption: reduce count of material (Material consumption)

docker exec 57213c12499a /bin/bash -c "echo '{"number": "test", "count": 55}' | /bin/kafka-console-producer --topic materials --bootstrap-server PLAINTEXT://localhost:9092"
docker exec 9470835a286f /bin/sh -c 'echo "{dffffd: ddd}" | kcat -b kafka:19092 -t test -P'


__TypeId__:consumption  	{"number": "test22", "count": 58}


**Material model:**  
number: text  
type: enum  
name: [map]  
group_number: text  
group_name: [map]  
profile: enum  
size: text  
profile: enum  
standard: text  
measure_unit: enum
count: Double
updatedAt: timestamp





/api/v1/materials  
/api/v1/purchased-items  
/api/v1/semi-parts  


docker exec 57213c12499a /bin/bash -c "echo '{"number": "test", "count": 55}' | /bin/kafka-console-producer --topic materials --bootstrap-server PLAINTEXT://localhost:9092"

authSource=admin

__TypeId__:receipt
{"number": "Steel-20X_GOST", "groupNumber": "Steel-40X", "type": 1, "name": "Сталь 40Х ГОСТ4543-2016", "groupName": "Сталь 40Х", "size": 22, "profile": 1, "standard": "ГОСТ4543-2016", "measureUnit": 1, "count": 1000}



mongodb://mongodb-user:mongodb-pwd@prodms-mongodb-0.prodms-mongodb-svc.prodms-infra.svc.cluster.local:27017/warehouse?replicaSet=prodms-mongodb&ssl=false
mongodb://mongodb-user:mongodb-pwd@prodms-mongodb-0.prodms-mongodb-svc.prodms-infra.svc.cluster.local:27017/admin?replicaSet=prodms-mongodb&ssl=false

