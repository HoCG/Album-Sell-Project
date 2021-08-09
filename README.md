# 1. 프로젝트 소개
이 프로젝트는 앨범을 판매하는 앨범샵 운영을 위한 판매 도움 프로젝트이며 재고를 관리해주고 사용자가 로그인을 해서 접속하면 그 이후부터 구매를 할수 있도록 만들었습니다. 자바프로그램을 기반으로 만들어졌으며 JFrame을 토대로 동작하는 프로그램입니다. 데이터베이스를 연동해서 재고를 명확하게 관리할 수 있게 만들었으며 데이터베이스의 경우는 마리아 DB를 통해 접근하도록 구현하였습니다. 데이터베이스는 album, artist, genre, orderedalbum, users테이블로 구성되어있으며 각각의 데이터들은 album의 경우는 album 정보와 갯수정보를 가지고, artist는 앨범을 발매한 아티스트의 정보를 가지며 genre는 장르의 정보들, orderedalbum은 유저가 주문한 앨범의 정보, users는 유저의 정보를 가집니다.

![Database](https://user-images.githubusercontent.com/52379503/128654408-0872e646-5465-43f5-be87-bbf34cdc682c.png)

# 2. 시작화면
처음 접근화면은 아래와같이 4개의 카테고리를 가진 화면이 출력되며 원하는 카테고리를 누르면 해당 카테고리로 이동합니다.
![1](https://user-images.githubusercontent.com/52379503/128654716-9c74a701-82cc-48f7-9d94-9e9531e7b3ea.png)

# 3. 정보관리
정보관리 버튼을 누르게되면 아래와 같은 화면이 출력되게 됩니다. 여기서 원하는 정보를 조회할 수 있습니다.
![2](https://user-images.githubusercontent.com/52379503/128654910-3ec96684-b2c5-40b4-b608-b2e5daebbb8a.png)

원하는 버튼을 누르게 되면 아래와 같은 정보들이 출력됩니다.
![3](https://user-images.githubusercontent.com/52379503/128654973-42a5a09f-ab4d-4367-b30f-bbfe98375558.png)

그리고 수정하고 싶은 사항이 있다면, 그 부분을 더블클릭할 시에 수정할 수 있도록 구현하였습니다.
![4](https://user-images.githubusercontent.com/52379503/128655005-280ecc38-2e87-4b21-bf92-244e829b55d9.png)

추가버튼을 누르게 된다면, 다이얼로그가 나오게 되며, 추가하고싶은 정보를 입력할 시 데이터를 추가하게 됩니다.
![5](https://user-images.githubusercontent.com/52379503/128655040-0fe9adf7-b43f-4226-ada1-4552575f76aa.png)

그리고 삭제버튼을 누르면 선택된 데이터를 삭제시킬 수 있습니다.
![6](https://user-images.githubusercontent.com/52379503/128655078-b15e334e-0be2-4bc6-bc10-edc7fd97cad4.png)

# 4. 구매
![7](https://user-images.githubusercontent.com/52379503/128655097-9d157e46-9cd9-4a39-932c-df425837c697.png)


