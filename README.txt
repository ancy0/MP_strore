SDK 버전은 안드로이드 12를 사용하였으며, 안드로이드 스튜디오에서 개발함 

첫번째 화면 (MainActivity.java, activity_main.xml)
-Relative Layout을 사용하여 화면 구성
- ID, 비밀번호, 로그인 버튼, 회원가입 버튼, Main버튼 구현되어 있음
- 첫번째 화면 출력 시 기존에 저장된 전체 사용자 ID ArrayList 를 preference를 이용해서 읽어 옴
- 로그인 버튼 클릭 시 입력한 ID가 가져온 전체 사용자 ID ArrayList에 존재하는지 확인 -> 존재하지 않는다면 Toast문 출력 후 입력한 ID와 비밀번호를 지움
- 입력한 ID가 유효한 ID라면 해당 ID를 key로 갖고 있는 사용자 정보 ArrayList를 preference를 이용해서 읽어 옴
- 입력한 비밀번호와 가져온 데이터의 비밀번호가 일치한지 확인
- ID 존재와 비밀번호 일치 모두 확인되면 로그인이 성공되며 intent로 세번째 화면으로 이동. 이때 putExtra로 사용자의 정보를 전달함
- 비밀번호가 일치하지 않아 로그인이 실패한 경우  Toast문 출력 후 입력한 비밀번호를 지워서 ID만 남겨둠 (편의성을 위함)

- 회원가입 버튼 클릭 시 두번째 화면인 회원가입 페이지로 이동
- Main버튼 클릭 시 세번째 화면인 상품을 보여주는 페이지로 이동

- 저장되어 있는 데이터를 가져올 때는 SharedPreferences에서 Json형식의 String을 가져와서 다시 ArrayList로 변환하여 가져옴


두번째 화면 (JoinActivity.java, activity_join.xml, border.xml)
- LinearLayout을 사용하여 화면 구성 (내용이 많기에 화면이 잘릴 경우를 대비하여 ScrollView 추가로 사용)
- 비밀번호는 특수문자를 제외하고 숫자, 영어를 사용하여 6~15문자까지 가능하도록 조건 설정
- 개인정보 사용 동의 약관은 RadioGroup을 사용하여 동의, 비동의 RadioButton이 둘 중 하나만 선택되도록 하고 동의를 선택한 경우 isAccept에 true값을 주도록 함
- ID 중복 확인 버튼 클릭 시 이미 중복을 확인했는지 확인하고 ID 입력이 비어있지 않은지 확인한다.
- ID가 제대로 입력되어 있다면 기존에 저장된 전체 사용자 ID ArrayList 를 preference를 이용해서 읽어 와 중복되는 값이 있는지 확인함
- 사용 가능한 ID인지, 중복된 ID인지에 대해  Toast문으로 출력하여 알려주고 사용가능한 ID라면 isCheckedID는 true로 설정
- 중복 확인을 하고도 입력한 ID를 수정할 수도 있기 때문에 ID입력에 변화가 있다면 isCheckedID를 false로 다시 설정

- 회원가입 완료 버튼 클릭 시 중복 체크 여부를 확인하고 모든 값이 입력되었는지 확인함. 모두 성립 시 비밀번호가 조건대로 입력되어있는지 확인함
- 마지막으로 약관 여부를 확인하여 동의 선택 시 ID, 비밀번호, 이름, 전화번호, 주소를 ArrayList에 넣고 이 ArrayList를 value로, ID를 key값으로 하여 프레퍼런스(Preference) 파일을 활용하여 회원정보를 저장함.
- ID는 다시 전체 사용자의 ID를 넣어둔 ArrayList에 추가하여 이 ArrayList를 value로 하고 String을 key값으로 갖는 데이터를 프레퍼런스(Preference) 파일에 저장함. 
- 사용자 정보가 저장되면 회원가입이 완료되고 다시 첫번째 페이지로 이동함
- 회원가입 실패의 모든 경우에선 Toast문을 출력하여 사용자가 무엇을 잘못입력하였는지 알려줌
-border.xml파일을 따로 생성하여 약관 내용에 테두리를 만들어줌

세번째 화면 (ViewItemActivity.java, StoreItem.java, activity_gridview.xml, activity_veiwitem.xml)
- constraintlayout과 GridView를 사용하고 GridView는 LinearLayout을 사용하여 화면 구성함
- ViewItemActivity에서 Adapter로 상품 정보를 받아 뷰를 생성해주고 이 Adapter를 사용하여 GridView에 사용자가 정의한 아이템을 표시하도록 함
- 이때, setAdapter() 를 통해 GridView와 Adapter를 연결
- activity_gridview.xml은 GridView 레이아웃 리소스임
- StoreItem.java에서는 GridView에 상품정보를 담기 위한 클래스가 정의되어 있으며 생성자 함스를 통해 각 멤버변수의 데이터 값을 넣어줌
- activity_veiwitem.xml은 상품의 뷰 레이아웃 리소스임

- 회원정보 버튼 클릭 시 hasExtra로 첫번째 화면에서 userData가 들어왔는지 확인
- 로그인이 되어있다면 getSerializableExtra로 데이터를 ArrayList로 변환
-회원인 경우는 가입한 회원 정보를 보여줌
- 회원이 아닌 경우는 회원정보 버튼을 클릭하면 회원가입 희망 여부에 따라  회원가입 버튼 클릭 시 회원가입 페이지인 두번째 화면으로 이동
