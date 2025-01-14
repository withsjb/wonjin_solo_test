Feature: wj

  Scenario: 북클럽 이동 확인
    Given 북클럽 버튼 클릭
    When 스마트씽크빅 버튼 클릭


  Scenario: 검색 실행 확인
    Given 라이브러리 버튼 클릭
    When 검색 버튼 클릭
    And 검색창에서 "네모, 안녕?" 입력
    And 검색창에서 검색 실행 버튼 클릭




