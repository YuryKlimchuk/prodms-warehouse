# Microservice **Warehouse**

Works with 3 models:
* Materials
* PurchasedItems
* SemiPart


## Materials

### Kafka consume events
* **Material receipt** - increase count of material or add new one
* **Material consumption** - reduce count of material

### API endpoints
* GET /api/v1/materials (pagination & filters) - get list
* GET /api/v1/materials/{number} - get detail info about certain material

