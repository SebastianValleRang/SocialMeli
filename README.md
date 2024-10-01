# Social Meli

---


## Integrantes

- Ana Maria García Acosta
- Angela Tatiana Daza Rojas
- Leandro Jossue Ramirez Vezga
- Sebastian Vallejo Rangel
- Vanessa Lozano Landinez

## Descripción del Proyecto

Social Meli es una aplicación desarrollada con Spring Boot que permite gestionar las relaciones entre usuarios y vendedores, sus publicaciones y productos en promoción.

## Tecnologías Utilizadas

- **SDK:** 22
- **Java:** 21
- **Spring Boot:** 3.3.4

### Dependencias

- **Spring Dev Tools**
- **Spring Web**
- **Lombok**

---

## Estructura del Proyecto

El proyecto está compuesto por las siguientes entidades:

- **User**
- **Post**
- **Product**

### Controladores y Servicios

Se implementan cuatro controladores, cada uno con sus respectivos servicios:

- **Post Controller**
- **Promo Controller**
- **Seller Controller**
- **User Controller**

A continuación, se presenta un diagrama que ilustra la estructura del proyecto, incluyendo los DTOs, repositorios e interfaces utilizadas:

<p align="center"><img src="src/main/resources/Diagrama.png" width="100%"></p>

---

## Endpoints y responsables

- **US 0001:** Poder realizar la acción de “Follow” (seguir) a un determinado vendedor.
  - **Endpoint:** /users/{userId}/follow/{userIdToFollow}
  - **Responsable:** Angela Daza.


- **US 0002:** Obtener el resultado de la cantidad de usuarios que siguen a un determinado vendedor
    - **Endpoint:** /users/{userId}/followers/count
    - **Responsable:** Vanessa Lozano.


- **US 0003:** Obtener un listado de todos los usuarios que siguen a un determinado vendedor
  - **Endpoint:** /users/{userId}/followers/list
  - **Responsable:** Vanessa Lozano.


- **US 0004:** Obtener un listado de todos los vendedores a los cuales sigue un determinado usuario (¿A quién sigo?)
  - **Endpoint:** /users/{userId}/followed/list
  - **Responsable:** Ana Garcia.


- **US 0005:** Dar de alta una nueva publicación
  - **Endpoint:** /products/post
  - **Responsable:** Leandro Ramírez.


- **US 0006:** Obtener un listado de las publicaciones realizadas por los vendedores que un usuario sigue en las últimas dos semanas (para esto tener en cuenta ordenamiento por fecha, publicaciones más recientes primero).
  - **Endpoint:** /products/followed/{userId}/list
  - **Responsables:** Leandro Ramírez y Sebastian Vallejo.


- **US 0007:** Poder realizar la acción de “Unfollow” (dejar de seguir) a un determinado vendedor.
  - **Endpoint:** /users/{userId}/unfollow/{userIdToUnfollow}
  - **Responsable:** Angela Daza.


- **US 0008:** Ordenamiento alfabético ascendente y descendente.
  - **Endpoint:** 
    - /users/{UserID}/followed/list?order=name_asc
    - /users/{UserID}/followed/list?order=name_desc
  - **Responsable:** Ana Garcia.
  - **Endpoint:** 
    - /users/{UserID}/followers/list?order=name_asc
    - /users/{UserID}/followers/list?order=name_desc
  - **Responsable:** Vanessa Lozano.


- **US 0009:** Ordenamiento por fecha ascendente y descendente
  - **Endpoint:** 
  - /products/followed/{userId}/list?order=date_asc
  - /products/followed/{userId}/list?order=date_desc
  - **Responsable:** Leandro Ramírez.


- **US 0010:** Llevar a cabo la publicación de un nuevo producto en promoción.
  - **Endpoint:** /products/promo-post
  - **Responsable:** Sebastian Vallejo.


- **US 0011:** Obtener la cantidad de productos en promoción de un determinado vendedor.
  - **Endpoint:** /products/promo-post/count?user_id={userId}
  - **Responsable:** Sebastian Vallejo.

### Bonus:

- **US 0012:** Listar los 5 vendedores con más posts
  - **Endpoint:** /users/list/most_active_sellers
  - **Responsables:** Leandro Ramírez y Sebastian Vallejo.

---
## Cómo Ejecutar el Proyecto

Para probar ejecutar y hacer uso del proyecto se cuenta con una collección de Postman con los request necesarios para verificar cada user story.
Este archivo se encuentra en: 
```src/main/resources/Sprint I.postman_collection.json
```



---

