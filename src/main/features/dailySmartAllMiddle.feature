@smartAllMiddle
Feature: DailySmartAllMiddle

  # 위클리에 동일한 시나리오 존재 (중복) >> 위클리로 한번만 등록해서 사용
  Scenario: 스마트올 중학 시작 (smartAllMiddle_000)
    Given 스마트올 중학 시작

# 위클리에 동일한 시나리오 존재 (중복) >> 위클리로 한번만 등록해서 사용
  Scenario: [AI학습관 - AI 영어 - 중학 필수 AI 영문법] 진단평가 응시하기 뷰어 확인 (smartAllMiddle_040)
    Given 스마트올 중학 홈 클릭
    When 상단 메뉴바 "AI학습관" 이동
    And AI 영어 클릭
    And 진단평가 응시하기 확인 및 클릭
    Then 진단평가 화면 확인


# 위클리에 동일한 시나리오 존재 (중복) >> 위클리로 한번만 등록해서 사용
  Scenario: [SMART내신] 메인 화면 확인 (smartAllMiddle_042)
    Given 스마트올 중학 홈 클릭
    When 상단 메뉴바 "SMART내신" 이동
    Then SMART내신 화면 확인

## 위클리에 동일한 시나리오 존재 (중복) >> 위클리로 한번만 등록해서 사용
#  Scenario: [SMART내신] 선택된 강의 학습 화면으로 이동 및 하위 버튼 클릭 (smartAllMiddle_045)
#    Given 스마트올 중학 홈 클릭
#    When 상단 메뉴바 "SMART내신" 이동
#    And 하단 첫번째 강의 클릭
#    Then 강의 화면 및 버튼 동작 확인
#
## 위클리에 동일한 시나리오 존재 (중복) >> 위클리로 한번만 등록해서 사용
#  Scenario: [수학전문관] 수학전문관 화면 노출 (smartAllMiddle_051)
#    Given 스마트올 중학 홈 클릭
#    When 상단 메뉴바 "수학전문관" 이동
#    And 수학전문관 화면 확인
#
## 위클리에 동일한 시나리오 존재 (중복) >> 위클리로 한번만 등록해서 사용
#  Scenario: [TEST 라운지 - 영.수모의고사] 모의고사 팝업 안내 (smartAllMiddle_055)
#    Given 스마트올 중학 홈 클릭
#    When 상단 메뉴바 "TEST라운지" 이동
#    And 드롭박스 "영·수 모의고사" 클릭
#    And 모의고사 첫 진입 팝업 확인
#
##    =============================
#  Scenario Outline: 상단 메뉴 드롭박스 변경 (DailySmartAllMiddle_001)
#    Given 스마트올 중학 홈 클릭
#    When 상단 메뉴바 "<menu>" 이동
#    And 중학 상단 드롭바 "<drop>" 클릭
#    Then 중학 상단 메뉴 "<drop>" 진입 확인
#
#    Examples:
#      |menu|drop|
#      |SMART내신|대치TOP|
#      |대치TOP|AI학습관|
#      |AI학습관|영어전문관|
#      |영어전문관|수학전문관|
#      |수학전문관|TEST라운지|
#      |TEST라운지|홈|
#
#  Scenario: 상단 검색 확인 (DailySmartAllMiddle_002)
#    Given 스마트올 중학 홈 클릭
#    When 중학 상단 검색 버튼 클릭
#    And 검색 수학 문항 검색 클릭
#    And 중학 검색창에 "이온" 입력
#    And 중학 검색창 검색 버튼 클릭
#    Then 중학 검색 확인
#
#  Scenario: [전체메뉴] AI학습관 확인 (DailySmartAllMiddle_003)
#    Given 스마트올 중학 홈 클릭
#    When 상단 햄버거 버튼 클릭
#    And 중학 전체메뉴 - "AI학습관" 클릭
#    Then 중학 "AI학습관" - "전체" 진입 확인
#
#  Scenario: 홈 내서랍 노출 확인 (DailySmartAllMiddle_004)
#    Given 스마트올 중학 홈 클릭
#    And 내서랍 클릭
#    Then 내서랍 화면 확인
#    And 중등 스마트올 홈으로
#
#  Scenario: 홈 중학 필수 어휘 학습 카드 확인 (DailySmartAllMiddle_005)
#    Given 스마트올 중학 홈 클릭
#    And 중학 필수 어휘 학습 카드 바로가기 클릭
#    Then 중학 필수 어휘 학습 VOCA 트레이닝 확인
#    And 중등 스마트올 홈으로
#
#  Scenario Outline: 홈 [대치TOP] 확인 (DailySmartAllMiddle_006)
#    Given 스마트올 중학 홈 클릭
#    And 홈 - 대치TOP "<submenu>" 클릭
#    Then 홈 "대치TOP" - "<submenu>" 이동 확인
#    And 대치TOP 0번째 강의 클릭
#    Then 대치TOP "<submenu>" 학습화면 확인
#    And 강의 학습화면 닫기버튼 클릭
#
#    Examples:
#      | submenu |
#      |C&A논술학원|
#      |세계로학원|
#      |에이프로아카데미|
#      |함영원영어학원|
#      |함수학학원|
#      |미래탐구(과학)|
#      |미래탐구(국어)|
#      |ILE|
#      |깊은생각|
#
#  Scenario: [AI학습관] 화면 확인 (DailySmartAllMiddle_007)
#    Given 스마트올 중학 홈 클릭
#    When 상단 메뉴바 "AI학습관" 이동
#    And AI 수학 클릭
#    And Ai 수학 "AI 리포트" 탭 클릭
#    Then Ai 수학 "AI 리포트" 화면 확인
#    And Ai 수학 "AI 추천 커리큘럼" 탭 클릭
#    And 진단평가 응시하기 확인 및 클릭
#    Then 진단평가 화면 확인
#
#
#
#  Scenario: [대치TOP] 메인 화면 확인 (DailySmartAllMiddle_008)
#    Given 스마트올 중학 홈 클릭
#    When 상단 메뉴바 "대치TOP" 이동
#    And 대치TOP 제목 설명란 확인
#    And 하단 첫번째 강의 클릭
#    Then 중학 "대치TOP" 강의 화면 확인
#
#
#
#  Scenario: [대치TOP] 학원 상세 화면 이동 (DailySmartAllMiddle_009)
#    Given 스마트올 중학 홈 클릭
#    When 상단 메뉴바 "대치TOP" 이동
#    And 하단 첫번째 강의 클릭
#    Then 중학 "대치TOP" 강의 화면 확인
#
#
#
#  Scenario Outline: [수학전문관] 수준별 영역별 강좌 이동 확인 (DailySmartAllMiddle_010)
#    Given 스마트올 중학 홈 클릭
#    When 상단 메뉴바 "수학전문관" 이동
#    And 수학전문관 "<icon>" 클릭
#    Then 수학전문관 화면 "<icon>" 변경 확인
#    And 하단 첫번째 강의 클릭
#    Then 중학 "수학전문관" 강의 화면 확인
#
#
#    Examples:
#      | icon |
#      | 입문   |
#      | 수와연산   |
#
#
#
#
#  Scenario Outline: [TEST 라운지] 서브메뉴별 전체 ui 확인 (DailySmartAllMiddle_011)
#    Given 스마트올 중학 홈 클릭
#    When 상단 메뉴바 "TEST라운지" 이동
#    And 드롭박스 "<submenu>" 클릭
#    Then "<submenu>" 검색필터 확인
#    And 기출문제 노출 확인
#    And 가이드? 버튼 클릭
#    Then 가이드 팝업 확인
#
#    Examples:
#      | submenu   |
#      | 영어듣기 능력평가 |
#      | 학력평가 기출문제 |