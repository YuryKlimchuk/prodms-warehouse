
# APIs

Materials (сырье)
PurchasedItems(покупное не требующие доработки)
SemiPart(полуфабрикаты)


## Materials API
* GET /api/v1/materials (pagination & filters) - get list
* POST /api/v1/materials - create new one
* GET /api/v1/materials/:id - get detail info about certain material
* PATCH /api/v1/materials/:id - patch material data

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





/api/v1/materials  
/api/v1/purchased-items  
/api/v1/semi-parts  