# Pet NFT

# :books: 목차

- [:book: 프로젝트 소개](#book-프로젝트-소개)

  - [프로젝트 개요](#프로젝트-개요)
  - [프로젝트 요약](#프로젝트-요약)
  - [프로젝트 플로우차트](#프로젝트-플로우차트)
  <!-- - [DB 스키마](#db-스키마) -->

- [:wrench: 사용 기술 (Technique)](#wrench-사용-기술-technique)

  - [:hammer:기술 스택 (Technique Used)](#기술-스택-technique-used)

    - [**FrontEnd**](#frontend)
    - [**Smart Contract**](#smart-contract)
    <!-- - [Connect](#connect) -->

- [:computer: 제공 기능 (Service)](#computer-제공-기능-service)

- [SmartContract](#SmartContract)

- [후기](#프로젝트후기)


# :book: 프로젝트 소개

## 프로젝트 개요

 블록체인 기술을 통해서 NFT를 이용하여 대체 불가능한 보증서를 제작 및 배포하여 
 
 이를 통해 반려동물의 정보를 확인하고 보증서를 통해 거래하는 애플리케이션 제작 

 IPFS 방식과 다르게 ON-CHAIN 방식으로 동물의 메타정보를 기록한다.

 더 높은 탈중앙성을 가지게 된 동물의 정보가 기재된 서류 가 위조되지 않게 NFT를 발급 받는다.
 
 발행한 NFT정보를 통해서 우수한 혈통 고정 및 순수 품종의 보전, 올바른 번식, 거래시장에서의 위험성 제거 등 여러 순기능을 가져오는 것이 목적이다.
 

## 프로젝트 요약

동물 보증서를 NFT로 발행하고 그 정보를 바탕으로 반려동물을 거래한다.

메타데이터는 IPFS가 아닌 ON-CHAIN으로 모두 구현하여 기존 NFT가 발행되는 방식보다

탈중앙성이 높은 방식이 사용되었다.


## 프로젝트 플로우차트

  ![](/Doc/img/flowchart2.png)

# :wrench: 사용 기술 (Technique)
  ![](/Doc/img/tech.png)


### FrontEnd

|                         Icon                         | Stack | Description     |
| :--------------------------------------------------: | :---: | --------------- |
| <img src = "/Doc/stackIcon/and.png" height = 25px> | Android Studio | 프론트앤드 구성 |

### Smart Contract

|                          Icon                           |  Stack   | Description          |
| :-----------------------------------------------------: | :------: | -------------------- |
| <img src = "/Doc/stackIcon/Solidity.png" height = 25px> | SOLIDITY | 스마트 컨트랙트 작성 |
| <img src = "/Doc/stackIcon/Truffle.png" height = 25px>  | TRUFFLE  | 스마트 컨트랙트 배포 |
|  <img src = "/Doc/stackIcon/klaytn.png" height = 25px>  | KLAYTN  | 블록체인 네트워크    |

<!-- ## :construction:Architecture

  ![](/Doc/img/architecture.png) -->



# :computer: 제공 기능 (Service)

## 홈화면 이미지 
<div align="center">
<img src="/Doc/img/login.png" alt="홈화면" width="200" height="400">

<!-- 정보 등록 이미지 -->
<img src="/Doc/img/account.png" alt="계정생성" width="200" height="400">

</div>

계정 생성버튼을 통해서 account를 생성하면 개인키가 발급된다.
로그인은 계정과 개인키를 입력하여 진행한다.


## 정보 기입후 업로드

<div align="center">
<img src="/Doc/img/feed.png" alt="피드확인" width="200" height="400">

</div>
사용자는 반려동물의 정보를 등록한다. 이때 반려동물의 사진을 등록할때는 사진이 byte배열로 변환되고

16진수 문자열로 변환되어 클레이튼 트랜잭션의 최대크기인 32kb를 초과하지않게 압축된 뒤에 트랜잭션이 발생한다.

가상화폐 지갑과 연결되지 않았기 때문에 입력받은 개인키를 통해서 rawtransaction으로 발생한다.

이러한 과정을 통해 onchain으로 메타정보를 기록할 수 있다.

# SmartContract


```java
  public  String byteArrayToHex(byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0) {
            return null;
        }
        StringBuilder stringBuffer = new StringBuilder(byteArray.length * 2);
        String hexNumber;
        for (byte aBa : byteArray) {
            hexNumber = "0" + Integer.toHexString(0xff & aBa);

            stringBuffer.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return stringBuffer.toString();
    }
```
이 함수를 통해 변형된 동물사진 데이터는 uploadPhoto트랜잭션의 bytes memory photo 파라미터로 넘어간다.

```solidity

pragma solidity ^0.5.6;

 contract luximal is ERC721, ERC721Enumerable {

    event PhotoUploaded (uint256 indexed tokenId, bytes photo, string title, string location, string description, uint256 timestamp);

    mapping (uint256 => PhotoData) private _photoList;

    struct PhotoData {
        uint256 tokenId;                       // Unique token id
        address[] ownerHistory;                // History of all previous owners
        bytes photo;                           // Image source encoded in uint 8 array format
        string title;                          // Title of photo
        string location;                       // Location where photo is taken
        string description;                    // Short description about the photo
        uint256 timestamp;                     // Uploaded time
    }

    동물의 메타정보가 담기게 될 구조체이다.

  /**
   * @notice _mint() is from ERC721.sol
   */
    function uploadPhoto(bytes memory photo, string memory title, string memory location, string memory description) public {
        uint256 tokenId = totalSupply() + 1;

        _mint(msg.sender, tokenId);

        address[] memory ownerHistory;

        PhotoData memory newPhotoData = PhotoData({
            tokenId : tokenId,
            ownerHistory : ownerHistory,
            photo : photo,
            title: title,
            location : location,
            description : description,
            timestamp : now
        });

        _photoList[tokenId] = newPhotoData;
        _photoList[tokenId].ownerHistory.push(msg.sender);

        emit PhotoUploaded(tokenId, photo, title, location, description, now);
    }

    동물의 메타정보를 사용자가 업로드할때 발생하는 함수이다. 

     function getPhoto (uint tokenId) public view
    returns(uint256, address[] memory, bytes memory, string memory, string memory, string memory, uint256) {
        require(_photoList[tokenId].tokenId != 0, "Photo does not exist");
        return (
            _photoList[tokenId].tokenId,
            _photoList[tokenId].ownerHistory,
            _photoList[tokenId].photo,
            _photoList[tokenId].title,
            _photoList[tokenId].location,
            _photoList[tokenId].description,
            _photoList[tokenId].timestamp);
    }

    tokenId값을 통해서 NFT정보를 불러온다. 기록된 동물 사진은 bytes배열 형태이며 bitmap으로 다시 변환해야한다.

 }
 ```
 
```java

  getPhoto함수를 통해 조회한 동물사진 배열은 

       public Bitmap byteArrayToBitmap( byte[] byteArray )
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length );
        return bitmap ;
    }

  이 함수를 통해서 bitmap으로 전환되어 사진으로 볼수있게 된다.





```
# 프로젝트후기

처음 시작한 팀 프로젝트이며 devdocs를 처음으로 빠짐없이 정독했던 프로젝트이다. NFT가 IPFS에 메타데이터를 기록하는 것이
애매한 탈중앙성을 가지는 것으로 판단하여서 onchain으로 메타정보를 기록하는 방식을 시도했다. 
또한 이때 당시 접한 대부분의 dApp은 웹형 태여서 이전에 학습한 android studio를 사용해보고 싶었고 마침 klaytn의 caver-java
라이브러리가 존재하여 네이티브 디앱을 도전했다. 

모든 게 처음이었기 때문에 솔리디티 코드를 공부하고 테스트넷에 배포하는 과정조차 매우 쉽지 않았다. 
transaction 한도가 32kb였기 때문에 사용자가 올린 사진을 byte배열로 변환 한 뒤 32kb 이하로 압축하고
16진수 string으로 변환하여 성공적인 트랜잭션을 발생시키는 과정이 너무 어려웠지만 많은 것을 깨달을 수 있었던 프로젝트이다.
기록한 트랜잭션을 getPhoto함수로 조회하고 배열 정보를 bitmap으로 변환하는 과정은 많이 걸리지 않았다.

아쉬웠던 것은 Klaytn Api Service를 사용하여 만든 사용자 정보를 단순 로그인 형식으로 구현한 것과
메타데이터를 onchain으로 업로드하는경우 많은 가스비가 지출된다는 점이었다. 아이러니하게 이러한 과정을 겪고 나니
IPFS 네트워크를 사용하여 DAPP을 구현해 보고 싶었고 이후의 프로젝트를 웹과 IPFS를 사용한 프로젝트를 기획하게 되었다.



