Feature: [웅진 Daily] bookClub

#  * 있는 시나리오 >> 정기배포 시 확인 포함 할 시나리오

# ==================== 북클럽 ====================

  Scenario: 북클럽 메인 나의 프렌즈 확인 (CheckBookClub_001)
    Given 북클럽 버튼 클릭
    When 스마트씽크빅 버튼 클릭
    And 바나쿠 캐릭터 클릭
    Then 바나쿠 캐릭터 설정
    Then 북클럽 프렌즈 메뉴 확인
    Then 북클럽 나의 프렌즈 ON_OFF
    Then My 프로필 메인 구성 확인
    And My 프로필 나가기


  Scenario: 북클럽 상단 GNB 확인 (CheckBookClub_002)
    Given 북클럽 버튼 클릭
    When 라이브러리 초등 홈 화면 클릭
    Then 라이브러리 화면 구성 확인
    When 투데이 버튼 클릭
    Then 투데이 화면 구성 확인
    When 스마트씽크빅 버튼 클릭
    Then 스마트씽크빅 화면 구성 확인

# ==================== 투데이 ====================
#  1. 임의의 컨텐츠 선택
  Scenario: [투데이 - AI맞춤] 콘텐츠 확인 (CheckBookClub_003)
    Given 투데이 버튼 클릭
    When 투데이 - "AI맞춤 투데이" 서브메뉴 클릭
    Then AI맞춤 "이번 주 추천 책" 콘텐츠 확인
    And 5초 대기
    And 속속 캐스트북 확인 버튼 클릭
    And Reading continue "No"
    And 오디오북 종료하기
    And 짝꿍책 팝업 나가기



  Scenario: [투데이] 서브메뉴 구성 확인 (CheckBookClub_004)
    Given 투데이 버튼 클릭
    When 투데이 - "스마트" 서브메뉴 클릭
    Then 투데이 스마트 독서 구성 확인
    And 모두의 문해력 클릭
    Then 투데이 - 모두의 문해력 확인
    When 투데이 - "교과" 서브메뉴 클릭
    Then 교과 투데이 화면구성 확인


  Scenario: [투데이 - 메타버스] 구성 확인 (CheckBookClub_005)
    Given 투데이 버튼 클릭
    When 투데이 - "메타버스" 서브메뉴 클릭
    And 메타버스 콘텐츠 클릭
    And 15초 대기
    Then 메타버스 콘텐츠 확인

# ==================== 스마트씽크빅 ====================
  # 메타버스, 임의의 과목 - 국어/수학 과목으로 지정

  Scenario: [스마트씽크빅 - 메타버스] 실행 확인 (CheckBookClub_006)*
    Given 북클럽 버튼 클릭
    When 스마트씽크빅 버튼 클릭
    And 스마트씽크빅 메타버스 과목 클릭
    And 15초 대기
    Then 메타버스 콘텐츠 확인


  Scenario Outline: [스마트씽크빅 - 국어] 문항 뷰어 실행 확인 (CheckBookClub_007)*
    Given 북클럽 버튼 클릭
    When 스마트씽크빅 버튼 클릭
    And "<subject>" 과목 클릭
    And "<subject>" 학습단계 확인 버튼 클릭
    And "<subject>" 문항뷰어 확인 단계 클릭
    And "<subject>" 과목 "1호" 단계 클릭
    And 학습 "<subject>" 과목 "5일차" 클릭
    And 국어 과정테스트 클릭
    Then "<subject>" 과정테스트 확인
    And 웅진씽크빅 홈 화면 이동

    Examples:
      | subject |
      | 국어 |
      | 초등국어 |


  Scenario: [학습 - 개정수학] 문항 뷰어 확인 (CheckBookClub_008)*
    Given 북클럽 버튼 클릭
    When 스마트씽크빅 버튼 클릭
    And "개정수학" 과목 클릭
    And "개정수학" 학습단계 확인 버튼 클릭
    And 개정수학 D단계 클릭
    And "개정수학" 과목 "1호" 단계 클릭
    And 확인문제1 영역 콘텐츠 클릭
    And 10초 대기
    Then 문항뷰어 화면 확인
    And 학습 종료 버튼 클릭
    Then 문항 "과목 메인으로 이동할까요?" 팝업 확인
    And 문항 확인 버튼 클릭
    And 문항 홈 버튼 클릭


  Scenario: [라이브러리 - 홈] 북클럽 배너 확인 (CheckBookClub_009)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "홈" 서브메뉴 클릭
    And 라이브러리 홈 "초등" 버튼 클릭
    Then 라이브러리 홈 "배너" 확인
    And 라이브러리 홈 "유아" 버튼 클릭
    Then 라이브러리 홈 "배너" 확인


# 계정 학년 설정 : 1,2,3 == 저학년 : X200 / 4,5,6 == 고학년 : T500
  Scenario: [라이브러리 - 초등홈] 북클럽 강력 추천 학년 확인 (CheckBookClub_010)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "홈" 서브메뉴 클릭
    And 라이브러리 홈 "초등" 버튼 클릭
    And 라이브러리 홈 북클럽 강력 추천-"전문가 추천" 콘텐츠 클릭
    Then 초등홈 북클럽 강력 추천-"전문가 추천" 학년 확인
    And 북클럽 뒤로가기
    And 라이브러리 홈 북클럽 강력 추천-"교과수록 필독서" 콘텐츠 클릭
    Then 초등홈 북클럽 강력 추천-"교과수록 필독서" 학년 확인
    And 북클럽 뒤로가기



  Scenario Outline: [라이브러리 - 홈] 북클럽 오리지널 진입 확인 (CheckBookClub_011)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "홈" 서브메뉴 클릭
    And 라이브러리 홈 "<year>" 버튼 클릭
    And 라이브러리 "<year>"홈 "<menu>" 클릭
    Then 라이브러리 "<year>"홈 "<menu>" 진입 확인
    And 북클럽 뒤로가기

    Examples:
      |year|menu|
      |초등|북클럽 오리지널|
      |초등|인기 시리즈|
      |유아|북클럽 오리지널|


  Scenario Outline: [라이브러리 - 초등홈] 인기 분야 진입 확인 (CheckBookClub_012)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "홈" 서브메뉴 클릭
    And 라이브러리 홈 "초등" 버튼 클릭
    Then 라이브러리 초등홈 분야별 인기 메뉴 "<title>" 진입 확인

    Examples:
      | title |
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
      | 6 |
      | 전체보기 |


  Scenario Outline: [라이브러리 - 유아홈] 인기 분야 진입 확인 (CheckBookClub_013)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "홈" 서브메뉴 클릭
    And 라이브러리 홈 "유아" 버튼 클릭
    Then 라이브러리 유아홈 분야별 인기 메뉴 "<title>" 진입 확인

    Examples:
      | title |
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
      | 전체보기 |


# 이번주 라벨 노출 확인/썸네일 노출 확인
  Scenario: [라이브러리 - 북클럽 레터] 확인 (CheckBookClub_014)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "북클럽 레터" 서브메뉴 클릭
    And 라이브러리 북클럽 레터 "유아" 탭 클릭
    Then 북클럽 레터 회면 구성 확인
    And 라이브러리 북클럽 레터 "초등" 탭 클릭
    Then 북클럽 레터 회면 구성 확인


# ==================== 라이브러리 내책장 ====================

  Scenario Outline: [라이브러리 - 내책장] 상단 영역 확인 (CheckBookClub_015)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "내 책장" 서브메뉴 클릭
    Then 라이브러리 내 책장 "<bookLabel>" 확인
    And 라이브러리 내 책장 "<bookLabel>" 콘텐츠 클릭
    Then 라이브러리 내 책장 "<bookLabel>" 콘텐츠 확인

    Examples:
      | bookLabel |
      | 읽던 책 끝까지 읽기 |
      | 생각 표현하기 |

# ==================== 라이브러리 전체메뉴 ====================

  Scenario Outline: [라이브러리 - 전체메뉴] 교과 확인 (CheckBookClub_016)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then "<menu>" 교과 연계 도서 진입 확인

    Examples:
      |year|menu|
      |2|누리 1단계|
      |2|누리 2단계|
      |2|누리 3단계|
      |3|예비 초등|
      |3|초등 1학년|
      |3|초등 2학년|
      |4|초등 3학년|
      |4|초등 4학년|
      |4|초등 5학년|
      |4|초등 6학년|
      |5|중등|


  Scenario Outline: [라이브러리 - 전체메뉴] 언어 확인 (CheckBookClub_017)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      |year|menu|
      |1|옹알종알 말놀이|
      |1|그림책 안녕?|
      |2|가나다 한글|
      |2|한자 놀이|
      |2|옛이야기 솔솔|
      |2|세계명작 그림책|
      |2|이야기 그림책|
      |3|똑똑한 국어|
      |3|한자 탐험|
      |3|지혜의 고전|
      |3|우리들 이야기|
      |4|신화와 고전|
      |4|클래식 문학|
      |4|모험과 판타지|



  Scenario Outline: [라이브러리 - 전체메뉴] 사회 확인 (CheckBookClub_018)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      |year|menu|
      |1|나는 자라요!|
      |1|마음이 어때?|
      |2|별별 장소와 직업|
      |2|대단해! 인물|
      |2|마음이 어때?|
      |2|다양한 세상|
      |3|대단한 인물들|
      |3|궁금한 사회|
      |3|생각 넓히기|
      |4|직업과 경제|
      |4|역사 속으로|


  Scenario Outline: [라이브러리 - 전체메뉴] 수과학 확인 (CheckBookClub_019)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      |year|menu|
      |1|위대해! 자연|
      |1|신기해! 수과학|
      |2|신기해! 과학|
      |2|1,2,3 숫자|
      |2|창의사고력 수학 팩토|
      |3|팡팡 과학 실험|
      |3|요리조리 코딩|
      |4|놀라운 자연|
      |4|신기한 과학|
      |4|야무진 수학|


  Scenario Outline: [라이브러리 - 전체메뉴] 예술문화 확인 (CheckBookClub_020)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      |year|menu|
      |1|알록달록 예술놀이|
      |1|신나는 몸놀이|
      |2|랄랄라 동요|
      |2|미술이 좋아!|
      |2|쭈욱 체육|
      |2|조물조물 만들기|
      |2|궁금해! 지식백과|
      |2|스스로 생활 습관|
      |3|조심조심 안전|
      |4|아름다운 예술|



  Scenario Outline: [라이브러리 - 전체메뉴] 인지지식 확인 (CheckBookClub_021)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      |year|menu|
      |1|열려라! 감각|
      |1|오늘도 즐거워!|
      |2|궁금해! 지식백과|
      |2|스스로 생활 습관|
      |3|상식이 쑥쑥|
      |4|슬기로운 생활|


  Scenario Outline: [라이브러리 - 전체메뉴] 영어 확인 (CheckBookClub_022)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      |year|menu|
      |1|Songs|
      |2|Animations|
      |3|Storybooks|
      |4|Readers|
      |4|Activities|


  Scenario Outline: [라이브러리 - 전체메뉴] 음원 확인 (CheckBookClub_023)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      |year|menu|
      |1|들어 봐! 클래식|
      |2|들어 봐! 동요|
      |2|들어 봐! 동화|


  Scenario Outline: [라이브러리 - 전체메뉴] 플러스 확인 (CheckBookClub_024)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      |year|menu|
      |1|곰돌이 베이비|
      |1|인터랙티브북|
      |2|곰돌이 킨더|
      |2|곰돌이 스쿨|
      |2|창의아트깨치기|
      |2|과학탐구|
      |2|오롱도로롱 옛이야기|
      |2|상상몰랑|
      |2|내 친구 메이지|
      |3|탐험캡슐|
      |3|원리과학|
      |3|ARpedia|
      |3|AR Science|


  Scenario Outline: [라이브러리 - 전체메뉴] 킨더 확인 (CheckBookClub_025)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      | year | menu    |
      | 2    | 안전생활 습관 |
      | 2    | 의사소통    |
      | 2    | 사회관계    |
      | 2    | 자연탐구    |
      | 2    | 예술경험    |
      | 2    | 신체운동건강  |


  Scenario: [라이브러리 - 전체메뉴] 전집구독 확인 (CheckBookClub_026)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "4" "책다른 구독" 클릭
    Then 전체메뉴 "책다른 구독" 진입 확인


  Scenario Outline: [라이브러리 - 전체메뉴] 추천 필독서 확인 (CheckBookClub_027)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      | year | menu |
      |3|교과 수록|
      |4|추천 도서|
      |5|픽션|
      |5|논픽션|


  Scenario Outline: [라이브러리 - 전체메뉴] 문해력맘스 확인 (CheckBookClub_028)
    Given 라이브러리 버튼 클릭
    When 라이브러리 - "전체 메뉴" 서브메뉴 클릭
    And 라이브러리 전체메뉴 "<year>" "<menu>" 클릭
    Then 전체메뉴 "<menu>" 진입 확인

    Examples:
      | year | menu |
      |4|순공 챌린지|
      |5|맘스클럽|