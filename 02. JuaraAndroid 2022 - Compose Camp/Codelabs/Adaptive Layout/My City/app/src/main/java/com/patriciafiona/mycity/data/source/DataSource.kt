package com.patriciafiona.mycity.data.source

import android.view.Menu
import com.patriciafiona.mycity.data.MenuType
import com.patriciafiona.mycity.data.model.Data

object DataSource {

    val defaultData = Data(
        id = "-1",
        name = "Template Data",
        desc = "This is template data",
        location = "Somewhere else",
        imageUrl = "",
        type = MenuType.Museum
    )

    fun getAllData(): ArrayList<Data>{
        return arrayListOf(
            Data(
                id = "M-01",
                name = "Museum Nasional Indonesia",
                desc = "Museum Nasional Republik Indonesia, atau yang sering disebut dengan Museum Gajah, adalah sebuah museum arkeologi, " +
                        "sejarah, etnografi, dan geografi yang terletak di Jakarta Pusat dan persisnya di Jalan Merdeka Barat 12." +
                        " Museum ini merupakan museum pertama dan terbesar di Asia Tenggara.",
                established = "24 April 1778",
                location = "Jl. Medan Merdeka Barat No. 12\n" +
                        "Kelurahan Gambir, Kecamatan Gambir\n" +
                        "Jakarta Pusat 10110",
                category = "Museum ilmu pengetahuan",
                websiteUrl = "http://www.museumnasional.or.id/",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Museum_Nasional_Indonesia.jpg/500px-Museum_Nasional_Indonesia.jpg",
                type = MenuType.Museum
            ),
            Data(
                id = "M-02",
                name = "Museum Sasmitaloka Jenderal Besar DR. A. H. Nasution",
                desc = "Museum Abdul Haris Nasution atau tepatnya Museum Sasmitaloka Jenderal Besar DR. Abdul Haris Nasution" +
                        " adalah salah satu museum pahlawan nasional yang terletak di jalan Teuku Umar No. 40, Jakarta Pusat, " +
                        "DKI Jaya, Indonesia. Museum ini terbuka untuk umum dari hari Selasa hingga hari Minggu, dari pukul " +
                        "08:00 hingga pukul 14:00 WIB. Setiap hari Senin museum ini ditutup untuk umum.",
                established = "3 Desember 2008",
                location = "Jalan Teuku Umar No. 40,\n" +
                        "Kelurahan Gondangdia, Kecamatan Menteng\n" +
                        "Kota Jakarta Pusat 10350",
                category = "Museum Pahlawan Nasional",
                type = MenuType.Museum,
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/MuseumJendralAHNasution.jpg/500px-MuseumJendralAHNasution.jpg"
            ),
            Data(
                id = "M-03",
                name = "Museum Katedral",
                desc = "Museum ini diresmikan pada tanggal 28 April 1991 oleh Mgr Julius Darmaatmadja. Pembuatan museum Katedral diprakarsai " +
                        "oleh pastor kepala Katedral pada waktu itu, yaitu Pater Rudolf Kurris. Hal ini berawal dari rasa cinta Kurris terhadap " +
                        "sejarah dan benda-benda bersejarah. Menurutnya, benda-benda bersejarah itu dapat membangkitkan rasa kagum manusia terhadap " +
                        "masa lampau dan keinginannya menyalurkan pengetahuan dari generasi ke generasi. Museum Katedral ini berada di ruang " +
                        "balkon Katedral.",
                established = "28 April 1991",
                location = "Jl. Katedral No.7B, Ps. Baru, \n" +
                        "Kecamatan Sawah Besar, Kota Jakarta Pusat, \n" +
                        "Daerah Khusus Ibukota Jakarta 10710",
                category = "Museum Rohani",
                type = MenuType.Museum,
                websiteUrl = "http://www.katedraljakarta.or.id/",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Jakarta_Indonesia_Jakarta-Cathedral-10.jpg/550px-Jakarta_Indonesia_Jakarta-Cathedral-10.jpg"
            ),
            Data(
                id = "M-04",
                name = "Museum Perumusan Naskah Proklamasi",
                desc = "Museum Perumusan Naskah Proklamasi atau disingkat dengan Munasprok adalah gedung yang dibangun sebagai monument peristiwa proses perumusan naskah proklamasi kemerdekaan di Indonesia. " +
                        "Gedung luas tanah 3.914 meter persegi dan luas bangunan 1.138 meter persegi itu pertama kali didirikan pada tahun 1920 dengan gaya arsitektur Eropa. Di dalam gedung tersebut terdapat " +
                        "ruangan, mebel kuno, dan aksesoris yang menggambarkan suasana serupa peristiwa perumusan naskah proklamasi.",
                established = "24 November 1992",
                location = "Jl. Imam Bonjol No.1, RW.4, \n" +
                        "Menteng, Kec. Menteng, Kota Jakarta Pusat, \n" +
                        "Daerah Khusus Ibukota Jakarta 10310",
                category = "Museum Sejarah",
                type = MenuType.Museum,
                websiteUrl = "https://www.munasprok.go.id/",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/ProclamationMuseum.jpg/440px-ProclamationMuseum.jpg"
            ),
            Data(
                id = "M-05",
                name = "Museum Sumpah Pemuda",
                desc = "Museum Sumpah Pemuda adalah sebuah museum sejarah perjuangan kemerdekaan Republik Indonesia yang berada di Jalan Kramat Raya No. 106, Jakarta Pusat dan dikelola oleh Kementerian Pendidikan dan " +
                        "Kebudayaan Republik Indonesia.",
                established = "20 Mei 1974",
                location = "Jalan Kramat Raya No. 106,\n" +
                        "Jakarta Pusat, DKI Jaya\n" +
                        "Indonesia",
                category = "Museum ilmu pengetahuan",
                type = MenuType.Museum,
                websiteUrl = "museumsumpahpemuda.kemdikbud.go.id",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e2/MuseumSumpahPemuda.jpg/400px-MuseumSumpahPemuda.jpg"
            ),
            Data(
                id = "M-06",
                name = "Museum Taman Prasasti",
                desc = "Museum Taman Prasasti adalah sebuah museum cagar budaya peninggalan masa kolonial Belanda yang berada di Jalan Tanah Abang " +
                        "No. 1, Jakarta Pusat. Museum ini memiliki koleksi prasasti nisan kuno serta miniatur makam khas dari 27 provinsi di " +
                        "Indonesia, beserta koleksi kereta jenazah antik. Museum seluas 1,2 ha ini merupakan museum terbuka yang menampilkan " +
                        "karya seni dari masa lampau tentang kecanggihan para pematung, pemahat, kaligrafer dan sastrawan yang menyatu.",
                established = "9 Juli 1977,",
                location = "Jl. Tanah Abang I No.1, RT.11/RW.8, Petojo Sel., \n" +
                        "Kecamatan Gambir, Kota Jakarta Pusat, \n" +
                        "Daerah Khusus Ibukota Jakarta 10160",
                category = "Museum Peninggalan Belanda",
                type = MenuType.Museum,
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/75/Tamanprasasti.jpg/400px-Tamanprasasti.jpg"
            ),
            Data(
                id = "M-07",
                name = "Galeri Nasional Indonesia",
                desc = "Galeri Nasional Indonesia (bahasa Inggris: National Gallery of Indonesia) adalah sebuah " +
                        "lembaga budaya negara atau sebagai museum seni rupa modern dan kontemporer. Di sini terdapat " +
                        "gedung yang berfungsi antara lain sebagai tempat pameran, dan perhelatan seni rupa Indonesia dan " +
                        "mancanegara. Gedung ini merupakan institusi milik pemerintah di bawah Menteri Pendidikan dan " +
                        "Kebudayaan. Fungsi Galeri Nasional Indonesia adalah melaksanakan pengkajian, pengumpulan, " +
                        "registrasi, perawatan, pengamanan, pameran, kemitraan, layanan edukasi dan publikasi karya seni " +
                        "rupa. Lalu fungsi utamanya adalah melindungi, pengembangan, dan pemanfaatan asset kesenian (seni rupa) " +
                        "sebagai fasilitas pendidikan dan kebudayaan. Galeri Nasional beralamat di Jalan Medan Merdeka Timur " +
                        "No. 14 Jakarta Pusat.",
                established = "",
                location = "Jl. Medan Merdeka Tim. No.14, RT.6/RW.1, \n" +
                        "Gambir, Kecamatan Gambir, Kota Jakarta Pusat, \n" +
                        "Daerah Khusus Ibukota Jakarta 10110",
                category = "Musesum Seni",
                type = MenuType.Museum,
                websiteUrl = "https://gni.kemdikbud.go.id/",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/National_Gallery_of_Indonesia.JPG/440px-National_Gallery_of_Indonesia.JPG"
            ),
            Data(
                id = "M-08",
                name = "Museum Bank Mandiri",
                desc = "Museum Bank Mandiri terletak di Jl. Lapangan Stasiun No. 1, Jakarta Barat dan " +
                        "merupakan salah satu bagian dari cagar budaya Kota Tua di Jakarta. \n" +
                        "\n" +
                        "Museum dibangun pada tahun 2004. Museum yang menempati area seluas 10.039 m2 dengan luas " +
                        "gedung sebesar 21.504 M2 ini pada awalnya adalah gedung Nederlandsche Handel-Maatschappij (NHM) " +
                        "atau kantor Factorij di Batavia yang merupakan perusahaan dagang milik raja Belanda yaitu " +
                        "Willem I yang kemudian berkembang menjadi perusahaan di bidang perbankan.\n" +
                        "\n" +
                        "Nederlandsche Handel-Maatschappij (NHM) dinasionalisasi pada tahun 1960 menjadi salah satu " +
                        "gedung kantor Bank Koperasi Tani & Nelayan (BKTN) Urusan Ekspor Impor. Kemudian bersamaan " +
                        "dengan lahirnya Bank Ekspor Impor Indonesia (BankExim) pada 31 Desember 1968, gedung tersebut " +
                        "pun beralih menjadi kantor pusat Bank Export import (Bank Exim), hingga akhirnya legal " +
                        "merger Bank Exim bersama Bank Dagang Negara (BDN), Bank Bumi Daya (BBD) dan Bank " +
                        "Pembangunan Indonesia (Bapindo) ke dalam Bank Mandiri (1999), maka gedung tersebut pun " +
                        "menjadi asset Bank Mandiri.",
                established = "2 Oktober 1998",
                location = "Jl. Asemka No.1, RT.3/RW.6, \n" +
                        "Pinangsia, Kec. Taman Sari, Kota Jakarta Barat, \n" +
                        "Daerah Khusus Ibukota Jakarta 11110",
                type = MenuType.Museum,
                category = "Museum Perekonomian and Numismatik",
                websiteUrl = "https://www.museumindonesia.com/museum/54/1/Museum_Mandiri_Jakarta",
                imageUrl = "https://asset.kompas.com/crops/oX7RK_TY76ClHInRq0fuBAucbHQ=/91x0:934x562/750x500/data/photo/2020/03/14/5e6ce7464f123.jpg"
            ),
            Data(
                id = "M-09",
                name = "Museum Bank Indonesia",
                desc = "Museum Bank Indonesia adalah sebuah museum di Jakarta, Indonesia yang terletak di Jl. Pintu Besar Utara No. 3, " +
                        "Jakarta Barat (depan stasiun Beos Kota), dengan menempati area bekas gedung Bank Indonesia Kota yang merupakan " +
                        "cagar budaya peninggalan De Javasche Bank yang beraliran neo-klasikal, dipadu dengan pengaruh lokal, dan " +
                        "dibangun pertama kali pada tahun 1828.\n" +
                        "\n" +
                        "Pada tahun 1625, di tempat ini pernah dibangun sebuah gereja sederhana untuk umat Protestan. Pada tahun 1628, " +
                        "gereja ini dibongkar karena digunakan untuk tempat meriam besar ketika puluhan ribu tentara Sultan Agung " +
                        "menyerang Batavia untuk pertama kali.\n" +
                        "\n" +
                        "Museum ini menyajikan informasi peran Bank Indonesia dalam perjalanan sejarah bangsa yang dimulai sejak " +
                        "sebelum kedatangan bangsa barat di Nusantara hingga terbentuknya Bank Indonesia pada tahun 1953 dan " +
                        "kebijakan-kebijakan Bank Indonesia, meliputi pula latar belakang dan dampak kebijakan Bank Indonesia " +
                        "bagi masyarakat sampai dengan tahun 2005. Penyajiannya dikemas sedemikian rupa dengan memanfaatkan " +
                        "teknologi modern dan multi media, seperti display elektronik, panel statik, televisi plasma, dan " +
                        "diorama sehingga menciptakan kenyamanan pengunjung dalam menikmati Museum Bank Indonesia. Selain " +
                        "itu terdapat pula fakta dan koleksi benda bersejarah pada masa sebelum terbentuknya Bank Indonesia, " +
                        "seperti pada masa kerajaan-kerajaan Nusantara, antara lain berupa koleksi uang numismatik yang " +
                        "ditampilkan juga secara menarik.\n" +
                        "\n" +
                        "Peresmian Museum Bank Indonesia dilakukan melalui dua tahap, yaitu peresmian tahap I dan mulai dibuka " +
                        "untuk masyarakat (soft opening) pada tanggal 15 Desember 2006 oleh Gubernur Bank Indonesia saat itu, " +
                        "Burhanuddin Abdullah, dan peresmian tahap II (grand opening) oleh Presiden RI Susilo Bambang Yudhoyono, " +
                        "pada tanggal 21 Juli 2009.\n" +
                        "\n" +
                        "Museum Bank Indonesia buka setiap hari kecuali Senin dan hari libur nasional.",
                established = "21 Juli 2009",
                location = "Jl. Pintu Besar Utara No. 3, Jakarta Barat",
                category = "Museum Perekonomian and Numismatik",
                type = MenuType.Museum,
                websiteUrl = "https://www.bi.go.id/id/layanan/museum-bi/default.aspx",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Museum_Bank_Indonesia01.jpg/1062px-Museum_Bank_Indonesia01.jpg"
            ),
            Data(
                id = "M-10",
                name = "Museum Bahari",
                desc = "Museum Bahari adalah museum yang menyimpan koleksi yang berhubungan dengan kebaharian dan kenelayanan bangsa " +
                        "Indonesia dari Sabang hingga Merauke yang berlokasi di seberang Pelabuhan Sunda Kelapa. Museum adalah salah " +
                        "satu dari delapan museum yang berada di bawah pengawasan dari Dinas Kebudayaan Permuseuman Provinsi Daerah " +
                        "Khusus Ibu kota Jakarta.",
                established = "",
                location = "Jl. Ps. Ikan No.1, RT.11/RW.4, \n" +
                        "Penjaringan, Kec. Penjaringan, Kota Jkt Utara, \n" +
                        "Daerah Khusus Ibukota Jakarta 14440",
                category = "Museum Ilmu Pengetahuan",
                type = MenuType.Museum,
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Museum_Bahari_Pintu_Masuk.jpg/600px-Museum_Bahari_Pintu_Masuk.jpg"
            ),
            Data(
                id = "SC-01",
                name = "Gajah Mada Plaza Mall",
                desc = "Mal bertingkat dengan department store, supermarket, toko retail lokal/global & restoran kasual.",
                location = "Jl. Gajah Mada No.19, RT.2/RW.1, \n" +
                        "North Petojo, Gambir, Central Jakarta City, \n" +
                        "Jakarta 10130",
                imageUrl = "https://infopromodiskon.com/userfiles/uploads/gajahmadaplaza1.jpg",
                type = MenuType.ShoppingCenter
            ),
            Data(
                id = "SC-02",
                name = "Central Park",
                desc = "Central Park Jakarta (atau biasa disingkat CP) atau Podomoro City Jakarta adalah kawasan terintegrasi " +
                        "(superblock) di Tanjung Duren Selatan, Grogol Petamburan, Jakarta Barat, Indonesia, yang terdiri dari " +
                        "sebuah pusat perbelanjaan mewah luxury shopping centre, satu menara perkantoran, tiga apartemen, dua " +
                        "theme park, sebuah resort, dan sebuah hotel mewah bertaraf internasional international luxury hotel " +
                        "chain dengan luas 655.000 m2 (7.050.000 sq ft) yang dibangun oleh Agung Podomoro Land dan dibuka secara " +
                        "resmi untuk umum oleh Presiden Republik Indonesia Susilo Bambang Yudhoyono pada tanggal 9 September 2009. " +
                        "Secara keseluruhan, kompleks Central Park Jakarta merupakan bangunan terbesar ke-8 di dunia. Namanya berasal " +
                        "dari Central Park di Manhattan, New York City. Central Park Jakarta melengkapi kaki langit wilayah Jakarta " +
                        "Barat dan terletak di antara Mall Taman Anggrek dan berdampingan dengan Neo Soho. Central Park Jakarta " +
                        "terletak di Kompleks Podomoro City, Jl. Letjen S. Parman Kav. 28, RT.12/RW.6, Tanjung Duren Selatan, " +
                        "Grogol Petamburan, Jakarta Barat, Daerah Khusus Ibukota Jakarta 11470. Tempat ini ramai dikunjungi oleh pelajar, mahasiswa, keluarga, dan para wisatawan dalam dan luar negeri.\n" +
                        "\n" +
                        "Bertepatan dengan hari ulang tahun Central Park Jakarta ke-7 tepatnya tanggal 9 September 2016, " +
                        "Neo Soho Podomoro City resmi dibuka untuk umum sepenuhnya sebagai pusat perbelanjaan yang memiliki " +
                        "jembatan Eco Skywalk yang terhubung langsung dengan Central Park Jakarta. Pusat perbelanjaan ini juga " +
                        "dilengkapi dengan Jakarta Aquarium di lantai LG. Pada tanggal 20 November 2019, The Food Hall dibuka di " +
                        "lantai LM.",
                location = "Jl. Letjen S. Parman No.kav.28, \n" +
                        "Tj. Duren Sel., Kec. Grogol petamburan, Kota Jakarta Barat, \n" +
                        "Daerah Khusus Ibukota Jakarta 11470",
                imageUrl = "https://ik.imagekit.io/tvlk/apr-asset/dgXfoyh24ryQLRcGq00cIdKHRmotrWLNlvG-TxlcLxGkiDwaUSggleJNPRgIHCX6/hotel/asset/67721384-800x600-FIT_AND_TRIM-325524a59cad094255e0c283564fb919.jpeg?tr=q-40,c-at_max,w-1280,h-720&_src=imagekit",
                websiteUrl = "https://www.centralparkjakarta.com/",
                type = MenuType.ShoppingCenter
            ),
            Data(
                id = "SC-03",
                name = "Grand Indonesia",
                desc = "Grand Indonesia merupakan mal di Jakarta. Mal ini dibuka pada tahun 2007 " +
                        "oleh Presiden Susilo Bambang Yudhoyono. Grand Indonesia merupakan Family Friendly " +
                        "Lifestyle Mall yang berkonsep untuk menyediakan seluruh kebutuhan keluarga dalam satu tempat. " +
                        "Pada tahun 2007, Hotel Indonesia mengalami pemugaran.",
                location = "Jl. M.H. Thamrin No.1, \n" +
                        "Kb. Melati, Kec. Menteng, Kota Jakarta Pusat, \n" +
                        "Daerah Khusus Ibukota Jakarta 10310",
                imageUrl = "https://www.grand-indonesia.com/wp-content/uploads/2019/04/Grand-Indonesia.jpg",
                websiteUrl = "https://www.grand-indonesia.com/",
                type = MenuType.ShoppingCenter
            ),
            Data(
                id = "SC-04",
                name = "Plaza Indonesia",
                desc = "Plaza Indonesia (atau biasa disingkat PI) dibuka secara resmi oleh Menteri Pariwisata, Pos, " +
                        "dan Telekomunikasi Republik Indonesia Joop Ave pada tanggal 1 Maret 1990, terdiri dari empat " +
                        "lantai pertokoan kelas atas dengan luas 38.050 m2. Pusat perbelanjaan ini terletak di Bundaran Hotel " +
                        "Indonesia, tepatnya pada perantara Jalan MH. Thamrin di kawasan bisnis utama Jakarta.",
                location = "Jl. M.H. Thamrin Kav. 28-30\n" +
                        "Jakarta 10350",
                imageUrl = "https://lirp.cdn-website.com/61d4ea87/dms3rep/multi/opt/rsz_inpp_plaza_indonesia-640w.jpg",
                websiteUrl = "https://www.plazaindonesia.com/",
                type = MenuType.ShoppingCenter
            ),
            Data(
                id = "SC-05",
                name = "Mall Taman Anggrek",
                desc = "Mal Taman Anggrek adalah sebuah pusat perbelanjaan yang terletak di Tanjung Duren Selatan, " +
                        "Grogol Petamburan, Jakarta Barat, Indonesia. Mal ini dibuka pada 28 Agustus 1996. Di pusat " +
                        "perbelanjaan ini, terdapat sebuah rink ice skating. Mall ini didirikan oleh Salimin Prawiro " +
                        "Sumarto, konglomerat asal Kebumen.",
                location = "Jl. Tj. Duren Timur 2, RT.12/RW.1, \n" +
                        "Tj. Duren Sel., Kec. Grogol petamburan, Kota Jakarta Barat, \n" +
                        "Daerah Khusus Ibukota Jakarta 11470",
                imageUrl = "https://cdn1-production-images-kly.akamaized.net/FUOsBfiltToa9MVtJseyQtRMxfk=/1200x1200/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/1772578/original/090713000_1510834599-IMG_8393.JPG",
                websiteUrl = "https://www.taman-anggrek-mall.com/",
                type = MenuType.ShoppingCenter
            ),
        )
    }
}