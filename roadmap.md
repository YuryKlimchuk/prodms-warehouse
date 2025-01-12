
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